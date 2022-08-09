# Capstone project của Nguyễn Ngọc Quang

File `application.properties` được cấu hình như sau, sử dụng mysql, port 33065, user, password được cấu hình như bên dưới, lưu ý rằng
- Tắt `spring.jpa.show-sql` để đỡ bị rối bởi các câu lệnh sql generate bởi hibernate, nếu cần debug hibernate thì ta lại bật lên
- `spring.jpa.hibernate.ddl-auto=none` và `spring.sql.init.mode=always` để bật tính năng khởi tạo cơ sở dữ liệu từ `schema.sql` và `data.sql`, dễ cho việc test đồ án
- `version` của `mysql` là `8.0.29`
- Sử dụng maven và build dự án, có thể chạy `mvn spring-boot:run` hoặc mở đồ án với `intelij` là có thể chạy thử rồi
- `backup-images` là thư mục backup các hình ảnh, để nếu test xóa hình ảnh thì vẫn còn `backup`

```script
server.port=8585
server.servlet.context-path=/ilovevn

spring.datasource.url=jdbc:mysql://localhost:33065/ilovevn_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=password

#spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

spring.mvc.view.prefix: /WEB-INF/jsp/
spring.mvc.view.suffix: .jsp

#spring.jpa.hibernate.ddl-auto=update
spring.jpa.hibernate.ddl-auto=none
spring.sql.init.mode=always
```

# Nội dung project

Video giới thiệu tính năng hiện tại: https://drive.google.com/file/d/1s821E_Z3jcBnpJz975VYbnO6cO5UV53J/view?usp=sharing
Chủ yếu là clone lại https://momo.vn/trai-tim-momo

Công nghệ sử dụng
* [x] Spring MVC và Spring Boot
* [x] Phần view sử dụng Java server page - jps
* [x] Persistence: sử dụng hibernate (provided by spring jpa), jpql hoặc `native query` nếu cần thiết
- [ ] Tổ chức code:
	- [ ] `archive` backup code thôi, không cần đế ý
	- [ ] entity: các class model, domain của project
	- [ ] controller
	- [ ] service
	- [ ] repository

Todo list:
- [ ]  Tổ chức phân trang dữ liệu đơn giản từ phía Client Side -> hiện tại làm 100% bằng server-side
* [x] Xóa một entity `project`
	- [x]  Cho phép chọn xoá nhiều phần tử cùng lúc
	- [x] Xác nhận trước khi thực hiện xoá
	- [x] Thông báo xoá thành công, xoá thất bại
	* [x] Hỗ trợ xoá thành công khi xoá thành công tất cả
	* [x] Khi xoá thất bại một phần tử thì ta không xoá bất cứ phần tử nào nữa
* [x] Tạo mới một `project`
	* [x] persist thành công vào bảng `projects`, casscade vào bảng `project_images` 
	* [x] copy file hình submit từ form vào server
* [x] Cập nhật một `project`
	* [x] persist thành công vào bảng `projects`, casscade vào bảng `project_images` 
	* [x] copy file hình mới, xóa file hình cũ
	
* [x] Load dữ liệu từ database
	* [x] Thực hiện phân trang bằng spring jpa, Page<Project>, read more at https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Page.html
	* [x] Có thể lọc theo tổ chức từ thiện, tìm kiếm không phân biệt hoa thường với tên dự án, sắp xếp theo trường tăng hoặc giảm dần
	- [ ] upgrade lên restful API kết hợp bootstrap table hoặc một framework js khác ở frontend
	
* [x] form validator
	* [x] validate khá đầy đủ với js  
	
# Các tính năng chưa xây dựng
- [ ] Quản lý các giao dịch quyên góp
- [ ] Quản lý user
- [ ] Login
- [ ] etc
	
