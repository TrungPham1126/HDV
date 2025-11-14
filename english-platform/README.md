# Anh Ngữ SOA Academy (Front-end)

Giao diện mô phỏng hệ thống bán và luyện tập khóa học IELTS được triển khai theo kiến trúc dịch vụ (SOA). Phần front-end được viết thuần **HTML/CSS/JavaScript** và giả lập việc kết nối tới các dịch vụ Java ở back-end.

## Cấu trúc thư mục

```
english-platform/
├── index.html          # Trang chính của ứng dụng
├── css/
│   └── styles.css      # Toàn bộ phong cách giao diện
├── js/
│   └── app.js          # Logic xử lý & mô phỏng gọi dịch vụ
└── assets/             # Nơi đặt hình ảnh/âm thanh bổ sung nếu cần
```

## Tính năng chính

- **Danh mục khóa học**: danh sách thẻ khóa học kiểu Udemy với bộ lọc trình độ và thanh tìm kiếm.
- **Giỏ hàng thông minh**: thêm/xem nhanh ngay tại biểu tượng 🛒; hỗ trợ mua ngay để mở trang chi tiết + QR MoMo.
- **Dashboard** (cần đăng nhập): hiển thị các khóa đã mua, mở workspace xem bài tập.
- **Luyện tập** (cần đăng nhập):
  - Speaking part 1/2/3 kèm nhập link hoặc tệp audio.
  - Writing Task 1/2 với mô tả biểu đồ/câu hỏi.
  - Bộ flashcard (quizlet) để ghi nhớ từ vựng.
- **Đăng bài học** (tài khoản giáo viên): gửi feedback, tải bài Reading/Listening (file .mp3) và công bố cho học viên.
- **Đăng nhập**: popup ở góc phải, mô phỏng việc gọi dịch vụ xác thực Java/Google OAuth. Phân biệt học viên/giáo viên bằng checkbox.
- **Tìm kiếm sản phẩm**: ô search chính giữa header lọc ngay danh sách khóa học.

## Cách sử dụng

1. Mở `index.html` bằng bất kỳ web server tĩnh nào (VS Code Live Server, `npx serve`, ...).
2. Khám phá danh mục khóa học, thêm vào giỏ hoặc bấm "Mua ngay" để xem chi tiết thanh toán.
3. Nhấn **Đăng nhập** → nhập email + mật khẩu:
   - Bỏ chọn "Tôi là giáo viên" để đăng nhập quyền học viên.
   - Chọn "Tôi là giáo viên" để mở thêm khu vực Đăng bài học.
4. Sau khi đăng nhập, truy cập Dashboard hoặc Luyện tập để gửi Speaking/Writing, xem flashcard.
5. Giáo viên dùng phần Đăng bài học để gửi feedback và tải bài Listening.

## Tuỳ biến

- Có thể chỉnh sửa danh sách khóa học tại `js/app.js` (`courseData`).
- Các câu hỏi Speaking/Writing và thẻ flashcard đều đặt trong file JS, dễ thay đổi.
- Nếu cần tích hợp thật với back-end Java, thay thế các hàm trong `ServiceGateway` bằng `fetch` gọi API thực tế.

## Yêu cầu hệ thống

- Trình duyệt hiện đại hỗ trợ ES modules.
- Nếu chạy qua máy chủ, đảm bảo cho phép tải file tĩnh (HTML/CSS/JS).
