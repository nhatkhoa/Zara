# Zara
# Yêu cầu 
1. Mongodb
2. Mysql (tạm thời, đang nghiên cứu để bỏ hẳn mysql)
3. Maven path (maven ở biến môi trường)
4. Sài eclipse or Intellji idea

# Cài đặt
1. Clone project về.
2. Import as maven nếu dùng eclipse
3. Vào Resource/Application.yaml để config database
4. Dùng terminal: mvn spring-boot:run để chạy web.
5. Truy cập vào http://localhost:8080/swagger-ui.html để test những api đã release.

# Mô tả

- Vì đây là phần Api (server) nên mọi đầu ra đều là api restFul.
- Để tiện cho việc test API nên sử dụng swagger (tự sinh html theo api có sẵn, cung cấp công tự test sẵn trên trang).
- Api xây dựng trên kiến truc DOMAIN DRIVEN (phân chia theo context, 1 domain bao gồm entity, repository, service, value liên quan tới nhau).
- Phần controller ở Web/Rest.
