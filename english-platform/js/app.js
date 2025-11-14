const ServiceGateway = {
  fetchCourses: () => Promise.resolve(courseData),
  purchaseCourse: (courseId) =>
    new Promise((resolve) => {
      setTimeout(() => resolve({ success: true, courseId }), 400);
    }),
  submitSpeaking: (payload) =>
    new Promise((resolve) => {
      setTimeout(() => resolve({ ...payload, id: Date.now() }), 500);
    }),
  submitWriting: (payload) =>
    new Promise((resolve) => {
      setTimeout(() => resolve({ ...payload, id: Date.now() }), 500);
    }),
  teacherFeedback: (payload) =>
    new Promise((resolve) => {
      setTimeout(() => resolve(payload), 400);
    }),
  publishTask: (payload) =>
    new Promise((resolve) => {
      setTimeout(() => resolve(payload), 400);
    }),
  auth: ({ email, password, isTeacher }) =>
    new Promise((resolve, reject) => {
      if (!email || !password) {
        reject(new Error('Vui lòng nhập email và mật khẩu'));
        return;
      }
      setTimeout(() => {
        resolve({
          token: 'fake-jwt',
          role: isTeacher ? 'teacher' : 'student',
          email,
        });
      }, 600);
    }),
};

const courseData = [
  {
    id: 'starter-foundation',
    title: 'IELTS Foundation mất gốc',
    mentor: 'Ms. Hương Trần',
    level: 'starter',
    duration: '32 giờ',
    rating: 4.8,
    students: 3200,
    price: 1290000,
    highlights: ['Từ vựng nền tảng', 'Ngữ pháp luyện theo dịch vụ', 'Speaking part 1-2 cơ bản'],
    description:
      'Làm quen IELTS từ số 0, luyện phát âm chuẩn, cài đặt lộ trình cá nhân hoá thông qua các dịch vụ Java.',
  },
  {
    id: 'skill-boost',
    title: 'Combo Speaking + Writing 6.0+',
    mentor: 'Thầy Quang',
    level: 'intermediate',
    duration: '40 giờ',
    rating: 4.9,
    students: 2100,
    price: 1990000,
    highlights: ['Phản hồi 48h', 'Kho câu hỏi mock test', 'Dịch vụ chấm bài tự động'],
    description:
      'Khoá chuyên sâu dành cho học viên muốn nâng band 6.0-7.0 với giáo viên Việt Nam & bản ngữ.',
  },
  {
    id: 'advanced-master',
    title: 'IELTS Advanced Master 7.5+',
    mentor: 'Dr. Minh Khuê',
    level: 'advanced',
    duration: '50 giờ',
    rating: 5,
    students: 1200,
    price: 2890000,
    highlights: ['Giáo viên bản ngữ', 'Tư vấn học bổng', 'Coaching 1-1'],
    description:
      'Khoá luyện đề chuyên sâu giúp bứt phá band 7.5+ với mô hình dịch vụ SOA giữa học viên - giáo viên - quản trị.',
  },
  {
    id: 'mocktest-pro',
    title: 'IELTS Mock Test Pro',
    mentor: 'Team Examiner',
    level: 'intermediate',
    duration: '20 giờ',
    rating: 4.7,
    students: 540,
    price: 890000,
    highlights: ['Đề chuẩn IDP', 'Phân tích điểm mạnh/yếu', 'Giao diện thi thật'],
    description:
      'Hệ thống đề thi thử tích hợp API mô phỏng phòng thi thật với thống kê tức thì.',
  },
];

const speakingQuestions = {
  part1: 'Giới thiệu bản thân và mô tả thói quen đọc sách của bạn.',
  part2:
    'Miêu tả một cuốn sách tiếng Anh đã giúp bạn cải thiện kỹ năng giao tiếp. Bạn đọc khi nào, nội dung gì và học được điều gì?',
  part3:
    'Theo bạn, việc đọc sách in có còn quan trọng trong thời đại công nghệ số? Vì sao?',
};

const writingPrompts = {
  task1:
    'Biểu đồ sau thể hiện số lượng học viên thi IELTS tại Việt Nam giai đoạn 2018-2023. Hãy mô tả xu hướng nổi bật.',
  task2:
    'Một số người tin rằng học online sẽ thay thế lớp học truyền thống. Bạn đồng ý hay phản đối? Nêu ví dụ cụ thể.',
};

const flashcards = [
  { term: 'meticulous', meaning: 'tỉ mỉ, kĩ lưỡng' },
  { term: 'articulate', meaning: 'nói rõ ràng mạch lạc' },
  { term: 'mitigate', meaning: 'giảm nhẹ tác động' },
  { term: 'resilience', meaning: 'khả năng phục hồi' },
  { term: 'compelling', meaning: 'thuyết phục' },
  { term: 'allocate', meaning: 'phân bổ' },
];

let cart = [];
let purchased = [];
let speakingSubmissions = [];
let writingSubmissions = [];
let publishedTasks = [];
let user = null;

const currency = (value) =>
  new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(value);

const el = (id) => document.getElementById(id);

const renderCourses = async () => {
  const list = el('courseList');
  const filter = el('levelFilter').value;
  const search = el('searchInput').value.toLowerCase();
  const courses = (await ServiceGateway.fetchCourses()).filter((course) => {
    const byLevel = filter === 'all' || course.level === filter;
    const bySearch =
      course.title.toLowerCase().includes(search) || course.description.toLowerCase().includes(search);
    return byLevel && bySearch;
  });

  list.innerHTML = courses
    .map(
      (course) => `
      <article class="card">
        <h3>${course.title}</h3>
        <p>${course.description}</p>
        <div class="course-meta">
          <span>${course.duration}</span>
          <span>${course.rating} ★ · ${course.students.toLocaleString()} học viên</span>
        </div>
        <div class="price">${currency(course.price)}</div>
        <div class="card-actions">
          <button data-id="${course.id}" class="secondary add-cart">Thêm giỏ hàng</button>
          <button data-id="${course.id}" class="primary buy-now">Mua ngay</button>
        </div>
      </article>`
    )
    .join('');
};

const updateCartUI = () => {
  el('cartCount').textContent = cart.reduce((sum, item) => sum + item.quantity, 0);
  if (!cart.length) {
    el('cartPreview').innerHTML = '<p>Giỏ hàng đang trống</p>';
    return;
  }
  const items = cart
    .map(
      (item) => `
      <div class="cart-item">
        <span>${item.title} ×${item.quantity}</span>
        <span>${currency(item.price * item.quantity)}</span>
      </div>`
    )
    .join('');
  const total = cart.reduce((sum, item) => sum + item.price * item.quantity, 0);
  el('cartPreview').innerHTML = `${items}<strong>Tổng: ${currency(total)}</strong>`;
};

const addToCart = (courseId) => {
  const course = courseData.find((c) => c.id === courseId);
  const existing = cart.find((c) => c.id === courseId);
  if (existing) existing.quantity += 1;
  else cart.push({ ...course, quantity: 1 });
  updateCartUI();
};

const showDetail = (courseId) => {
  const course = courseData.find((c) => c.id === courseId);
  const detail = el('productDetail');
  el('detailTitle').textContent = course.title;
  el('detailDescription').textContent = course.description;
  el('detailHighlights').innerHTML = course.highlights.map((h) => `<li>${h}</li>`).join('');
  el('detailLevel').textContent = `Trình độ: ${course.level}`;
  el('detailDuration').textContent = `Thời lượng: ${course.duration}`;
  el('detailPrice').textContent = currency(course.price);
  el('detailBuyBtn').onclick = async () => {
    await ServiceGateway.purchaseCourse(course.id);
    if (!purchased.find((c) => c.id === course.id)) {
      purchased.push(course);
      renderDashboard();
    }
    alert('Đã gửi yêu cầu thanh toán tới dịch vụ MoMo Java!');
  };
  detail.classList.remove('hidden');
  detail.scrollIntoView({ behavior: 'smooth' });
};

const renderDashboard = () => {
  const list = el('purchasedList');
  if (!purchased.length) {
    list.innerHTML = '<p>Chưa có khóa học nào. Mua ngay để kích hoạt dịch vụ.</p>';
    return;
  }
  list.innerHTML = purchased
    .map(
      (course) => `
    <article class="card">
      <h3>${course.title}</h3>
      <p>${course.description}</p>
      <button class="secondary open-workspace" data-id="${course.id}">Vào lớp</button>
    </article>`
    )
    .join('');
};

const openWorkspace = (courseId) => {
  const course = courseData.find((c) => c.id === courseId);
  const workspace = el('assignmentWorkspace');
  workspace.innerHTML = `
    <h3>Bài tập cho ${course.title}</h3>
    <p>Làm Speaking/Writing hoặc truy cập bài đọc do giáo viên giao.</p>
    <ul>
      <li>Video bài giảng mới nhất</li>
      <li>Link Google Classroom</li>
      <li>Lịch livestream tuần này</li>
    </ul>`;
  workspace.classList.remove('hidden');
  workspace.scrollIntoView({ behavior: 'smooth' });
};

const renderPracticeQuestions = () => {
  const speakingSelect = el('speakingPart');
  speakingSelect.innerHTML = Object.keys(speakingQuestions)
    .map((key) => `<option value="${key}">Part ${key.replace('part', '')}</option>`)
    .join('');
  updateSpeakingQuestion();
  updateWritingPrompt('task1');
};

const updateSpeakingQuestion = () => {
  const part = el('speakingPart').value;
  el('speakingQuestion').textContent = speakingQuestions[part];
};

const updateWritingPrompt = (task) => {
  document
    .querySelectorAll('.writing-tabs button')
    .forEach((btn) => btn.classList.toggle('active', btn.dataset.writing === task));
  el('writingPrompt').textContent = writingPrompts[task];
  el('writingSubmit').dataset.task = task;
};

const renderSpeakingSubmissions = () => {
  el('speakingSubmissions').innerHTML = speakingSubmissions
    .map(
      (sub) => `
      <div class="submission-item">
        <strong>${sub.part.toUpperCase()}</strong> · ${new Date(sub.createdAt).toLocaleString()}<br />
        Link: ${sub.link || 'Đã tải file'}<br />
        Feedback: ${sub.feedback || 'Chờ giáo viên'}
      </div>`
    )
    .join('');
};

const renderWritingSubmissions = () => {
  el('writingSubmissions').innerHTML = writingSubmissions
    .map(
      (sub) => `
      <div class="submission-item">
        <strong>${sub.task.toUpperCase()}</strong> · ${new Date(sub.createdAt).toLocaleString()}<br />
        Link: ${sub.link || 'Đã tải file'}<br />
        Feedback: ${sub.feedback || 'Chờ giáo viên'}
      </div>`
    )
    .join('');
};

const renderFlashcards = () => {
  el('flashcardGrid').innerHTML = flashcards
    .map(
      (card, index) => `
      <div class="flashcard" data-index="${index}">
        <span>${card.term}</span>
      </div>`
    )
    .join('');
};

const toggleCard = (index) => {
  const element = document.querySelector(`.flashcard[data-index='${index}']`);
  const flipped = element.classList.toggle('flipped');
  element.textContent = flipped ? flashcards[index].meaning : flashcards[index].term;
};

const renderFeedbackOptions = () => {
  const select = el('feedbackSubmission');
  const options = [...speakingSubmissions, ...writingSubmissions]
    .map((sub) => `<option value="${sub.id}">${sub.type} · ${sub.part || sub.task}</option>`)
    .join('');
  select.innerHTML = `<option value="">-- Chọn --</option>${options}`;
};

const renderPublishedTasks = () => {
  el('publishedTasks').innerHTML = publishedTasks
    .map(
      (task) => `
      <div class="submission-item">
        <strong>${task.type}</strong> · ${new Date(task.createdAt).toLocaleString()}<br />
        ${task.description}
      </div>`
    )
    .join('');
};

const setLoginState = (payload) => {
  user = payload;
  document.body.dataset.role = payload?.role || 'guest';
  el('loginBtn').textContent = user ? 'Đăng xuất' : 'Đăng nhập';
};

const requireLoginHandler = (targetId) => {
  if (!user) {
    toggleLoginModal(true);
    return;
  }
  document.getElementById(targetId)?.scrollIntoView({ behavior: 'smooth' });
};

const toggleLoginModal = (show) => {
  el('loginModal').classList.toggle('hidden', !show);
};

const setupEvents = () => {
  document.addEventListener('click', (event) => {
    if (event.target.matches('.add-cart')) {
      addToCart(event.target.dataset.id);
    }
    if (event.target.matches('.buy-now')) {
      showDetail(event.target.dataset.id);
    }
    if (event.target.matches('.open-workspace')) {
      openWorkspace(event.target.dataset.id);
    }
    if (event.target.matches('.practice-tabs button')) {
      document
        .querySelectorAll('.practice-tabs button')
        .forEach((btn) => btn.classList.toggle('active', btn === event.target));
      document.querySelectorAll('.practice-panel').forEach((panel) => panel.classList.add('hidden'));
      el(event.target.dataset.tab).classList.remove('hidden');
    }
    if (event.target.matches('.writing-tabs button')) {
      updateWritingPrompt(event.target.dataset.writing);
    }
  });

  el('levelFilter').addEventListener('change', renderCourses);
  el('searchBtn').addEventListener('click', renderCourses);
  el('searchInput').addEventListener('keyup', (e) => {
    if (e.key === 'Enter') renderCourses();
  });
  el('speakingPart').addEventListener('change', updateSpeakingQuestion);
  el('speakingSubmit').addEventListener('click', async () => {
    const payload = {
      type: 'speaking',
      part: el('speakingPart').value,
      link: el('speakingLink').value,
      createdAt: Date.now(),
    };
    const stored = await ServiceGateway.submitSpeaking(payload);
    speakingSubmissions.push(stored);
    renderSpeakingSubmissions();
    renderFeedbackOptions();
    alert('Đã gửi bài nói cho giáo viên');
  });

  el('writingSubmit').addEventListener('click', async (event) => {
    const payload = {
      type: 'writing',
      task: event.target.dataset.task,
      link: el('writingLink').value,
      createdAt: Date.now(),
    };
    const stored = await ServiceGateway.submitWriting(payload);
    writingSubmissions.push(stored);
    renderWritingSubmissions();
    renderFeedbackOptions();
    alert('Đã gửi bài viết!');
  });

  document.addEventListener('click', (event) => {
    if (event.target.classList.contains('flashcard')) {
      toggleCard(event.target.dataset.index);
    }
  });

  document.querySelectorAll('.require-login').forEach((btn) =>
    btn.addEventListener('click', () => requireLoginHandler(btn.dataset.target))
  );
  document.querySelectorAll('.require-teacher').forEach((btn) =>
    btn.addEventListener('click', () => {
      if (!user) {
        toggleLoginModal(true);
        return;
      }
      if (user.role !== 'teacher') {
        alert('Tính năng chỉ dành cho giáo viên được hệ thống cấp quyền.');
        return;
      }
      document.getElementById(btn.dataset.target)?.scrollIntoView({ behavior: 'smooth' });
    })
  );

  el('loginBtn').addEventListener('click', () => {
    if (user) {
      setLoginState(null);
    } else {
      toggleLoginModal(true);
    }
  });
  el('closeLogin').addEventListener('click', () => toggleLoginModal(false));
  el('submitLogin').addEventListener('click', async () => {
    try {
      const payload = await ServiceGateway.auth({
        email: el('email').value,
        password: el('password').value,
        isTeacher: el('teacherCheckbox').checked,
      });
      setLoginState(payload);
      toggleLoginModal(false);
      alert('Đăng nhập thành công qua dịch vụ Java!');
    } catch (error) {
      alert(error.message);
    }
  });

  el('feedbackBtn').addEventListener('click', async () => {
    const selected = el('feedbackSubmission').value;
    const content = el('feedbackContent').value.trim();
    if (!selected || !content) {
      alert('Chọn bài và nhập feedback');
      return;
    }
    await ServiceGateway.teacherFeedback({ selected, content });
    const updateFeedback = (list) => {
      const submission = list.find((item) => item.id.toString() === selected);
      if (submission) submission.feedback = content;
    };
    updateFeedback(speakingSubmissions);
    updateFeedback(writingSubmissions);
    renderSpeakingSubmissions();
    renderWritingSubmissions();
    renderFeedbackOptions();
    alert('Đã gửi feedback đến học viên');
  });

  el('publishTask').addEventListener('click', async () => {
    const description = el('readingTask').value.trim() || 'Bài đọc mới';
    const type = el('listeningAudio').files.length ? 'Listening' : 'Reading';
    const payload = await ServiceGateway.publishTask({ description, type, createdAt: Date.now() });
    publishedTasks.push(payload);
    renderPublishedTasks();
    alert('Bài luyện tập đã đăng thành công');
  });
};

const init = () => {
  renderCourses();
  renderPracticeQuestions();
  renderSpeakingSubmissions();
  renderWritingSubmissions();
  renderFlashcards();
  renderFeedbackOptions();
  renderPublishedTasks();
  renderDashboard();
  updateCartUI();
  setupEvents();
};

init();
