# 🏨 Booking Hotel Mobile App

> Ứng dụng di động hỗ trợ **đặt phòng khách sạn/khu nghỉ dưỡng** nhanh chóng, quản lý dịch vụ tiện ích, và thanh toán trực tuyến tiện lợi.

---

## ✨ Giới thiệu

Với nhu cầu du lịch và nghỉ dưỡng ngày càng tăng, việc đặt phòng và quản lý dịch vụ trở nên quan trọng. Tuy nhiên, nhiều ứng dụng hiện nay chưa tối ưu trải nghiệm người dùng, thiếu sự minh bạch trong dịch vụ và quy trình thanh toán.  

👉 **Booking Hotel Mobile App** được phát triển nhằm mang lại:
- Trải nghiệm đặt phòng nhanh chóng, tiện lợi.
- Tích hợp đa dịch vụ (spa, ăn uống, đưa đón…).
- Thanh toán an toàn, minh bạch.
- Quản lý đặt phòng và dịch vụ cá nhân dễ dàng.

---

## 🎯 Mục tiêu dự án

- **Đặt phòng trực tuyến**: Tìm kiếm, lọc phòng theo nhu cầu.  
- **Quản lý dịch vụ**: Đặt kèm spa, ăn uống, đưa đón, giải trí.  
- **Thanh toán**: Hỗ trợ ví điện tử, thẻ ngân hàng.  
- **Quản lý người dùng**: Đăng ký, đăng nhập (Google OAuth), quản lý hồ sơ cá nhân.  
- **Quản trị hệ thống**: Admin quản lý phòng, dịch vụ, doanh thu, thống kê.  

---

## 🛠️ Công nghệ sử dụng

- **Frontend (Mobile)**: Android (Java, XML)  
- **Backend**: Spring Boot (RESTful API, JWT, OAuth2 Google)  
- **Database**: PostgreSQL  
- **Triển khai**: Render (Backend), Firebase/Cloudinary (Media)  
- **Khác**: Retrofit2 (API call), Glide (load ảnh), Postman, GitHub  

---

## 📂 Kiến trúc hệ thống

Ứng dụng theo mô hình **Client - Server**:
1. **Mobile App (Android)**: Giao diện người dùng, gọi API.  
2. **Backend API (Spring Boot)**: Xử lý logic, xác thực, quản lý dữ liệu.  
3. **Database (PostgreSQL)**: Lưu trữ thông tin người dùng, phòng, dịch vụ, đặt chỗ.  

---

## 🖼️ Chức năng chính

### Người dùng (Khách hàng)
- 🔍 Tìm kiếm và đặt phòng theo ngày, loại phòng, giá.  
- 📅 Quản lý lịch đặt phòng.  
- 🛎️ Đặt dịch vụ kèm theo (spa, ăn uống, đưa đón).  
- 💳 Thanh toán trực tuyến (ví điện tử, thẻ).  
- 👤 Quản lý tài khoản, đăng nhập Google.  

### Quản trị viên (Admin)
- 🏢 Quản lý phòng (CRUD).  
- 🛠️ Quản lý dịch vụ.  
- 📊 Thống kê doanh thu, báo cáo.  
- 👥 Quản lý người dùng.  

---

## 🚀 Cài đặt và chạy thử

### Yêu cầu:
- [x] Android Studio (API 24 trở lên).  
- [x] JDK 17.  
- [x] PostgreSQL + pgAdmin.  
- [x] Máy có kết nối Internet.  

### Các bước:
```bash
# Truy cập vào đường dẫn Drive sau và tải file apk về: https://drive.google.com/file/d/1S8WaCW36R7w5Zh4IWz9iLb6xRpSSPP0g/view?usp=drive_link
Vì là dự án môn học sẽ an toàn và không có mã độc giúp cho các bạn có thể lấy về để nghiên cứu và phát triển thêm.
