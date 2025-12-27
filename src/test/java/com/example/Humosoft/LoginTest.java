package com.example.Humosoft;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class LoginTest {

    @BeforeAll
    public static void setup() {
        // Cấu hình URL server gốc
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    @DisplayName("TC01: Login thành công (Admin) - Happy Path")
    public void testLogin_Success() {
        String payload = """
            {
                "username": "admin",
                "password": "admin123"
            }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/auth/signin")
                .then()
                .statusCode(200)
                .body("result", notNullValue())            // Token phải có
                .body("message", equalTo("login success")) // Message chuẩn
                .body("code", equalTo(200))
                .log().ifValidationFails();
    }

    @Test
    @DisplayName("TC02: Login thất bại - Sai mật khẩu")
    public void testLogin_WrongPassword() {
        String payload = """
            {
                "username": "admin",
                "password": "sai_mat_khau_roi_nhe"
            }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/auth/signin")
                .then()
                .statusCode(400) // Backend trả về 400
                .body("message", notNullValue())
                .log().ifValidationFails();
    }

    @Test
    @DisplayName("TC03: Login thất bại - Username không tồn tại")
    public void testLogin_UserNotFound() {
        String payload = """
            {
                "username": "nguyen_van_ma",
                "password": "123"
            }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/auth/signin")
                .then()
                .statusCode(400)
                .log().ifValidationFails();
    }

    @Test
    @DisplayName("TC04: Login thất bại - Bỏ trống Username/Password")
    public void testLogin_EmptyInput() {
        String payload = """
            {
                "username": "",
                "password": ""
            }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/auth/signin")
                .then()
                .statusCode(400)
                .log().ifValidationFails();
    }

    @Test
    @DisplayName("TC05: Login thất bại - Thử tấn công SQL Injection")
    public void testLogin_SqlInjection() {
        String payload = """
            {
                "username": "admin' OR '1'='1",
                "password": "password_fake"
            }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/auth/signin")
                .then()
                .statusCode(400) // Hệ thống phải chặn lại
                .log().ifValidationFails();
    }

    @Test
    @DisplayName("TC06: Login thất bại - Username quá dài (>500 ký tự)")
    public void testLogin_LongUsername() {
        String longUsername = "a".repeat(501);
        String payload = String.format("""
            {
                "username": "%s",
                "password": "123"
            }
        """, longUsername);

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/auth/signin")
                .then()
                .statusCode(400)
                .log().ifValidationFails();
    }

    @Test
    @DisplayName("TC07: Login thành công (Staff) - Kiểm tra phân quyền")
    public void testLogin_StaffSuccess() {
        // Đảm bảo user này có trong DB và là nhân viên
        String payload = """
            {
                "username": "vunguyen1",
                "password": "10112004"
            }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/auth/signin")
                .then()
                .statusCode(200)
                .body("result", notNullValue())
                .log().ifValidationFails();
    }

    @Test
    @DisplayName("TC08: Login thất bại - Tài khoản đã bị xóa/khóa")
    public void testLogin_DeletedAccount() {
        // Đảm bảo user này trong DB đã set is_deleted = true
        String payload = """
            {
                "username": "nguyenvanb",
                "password": "10112004"
            }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/auth/signin")
                .then()
                .statusCode(400) // Server phải chặn đăng nhập và trả về lỗi
                // .body("message", containsString("deleted")) // Nếu muốn check kỹ thông báo lỗi
                .log().ifValidationFails();
    }
}