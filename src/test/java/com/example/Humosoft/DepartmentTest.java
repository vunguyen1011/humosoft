package com.example.Humosoft;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Kiểm thử Quy trình Quản lý Phòng ban (Department Flow)")
public class DepartmentTest {

    private static String adminToken;

    // Các biến static để truyền dữ liệu giữa các bước test
    private static int deptId;
    private static String deptName;
    private static int userId;

    @BeforeAll
    @DisplayName("Cấu hình & Đăng nhập Admin")
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080";

        // 0. Đăng nhập Admin lấy Token
        String loginPayload = """
            { "username": "admin", "password": "admin123" }
        """;

        adminToken = given()
                .contentType(ContentType.JSON)
                .body(loginPayload)
                .post("/auth/signin")
                .then()
                .statusCode(200)
                .extract().path("result");
    }

    // ============================================================
    // BƯỚC 1: TẠO PHÒNG BAN (Happy Path)
    // ============================================================
    @Test
    @Order(1)
    @DisplayName("Step 1: Tạo phòng ban mới thành công")
    public void step1_CreateDepartment() {
        // Dùng timestamp để tên phòng không bao giờ bị trùng
        deptName = "Phong Test Flow " + System.currentTimeMillis();

        String payload = String.format("""
            {
                "departmentName": "%s",
                "description": "Phong ban test quy trinh E2E"
            }
        """, deptName);

        System.out.println(">>> 1. Creating Department: " + deptName);

        deptId = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/departments")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .extract().path("result.id");

        System.out.println(">>> Created Dept ID: " + deptId);
    }

    // ============================================================
    // BƯỚC 1.1: TEST VALIDATION (Negative Case)
    // ============================================================
    @Test
    @Order(2)
    @DisplayName("Validation: Tạo phòng ban với tên rỗng (Phải lỗi)")
    public void step1_1_CreateDepartment_Fail_EmptyName() {
        String payload = """
            {
                "departmentName": "",
                "description": "Ten rong thi phai loi"
            }
        """;

        given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/departments")
                .then()
                .statusCode(400); // Expect Bad Request
    }

    // ============================================================
    // BƯỚC 2: TẠO USER (Ban đầu đã thuộc phòng ban ở Bước 1)
    // ============================================================
    @Test
    @Order(3)
    @DisplayName("Step 2: Tạo User mới và gán vào phòng ban vừa tạo")
    public void step2_CreateUser() {
        long timestamp = System.currentTimeMillis();
        String uniqueEmail = "test_user_" + timestamp + "@gmail.com";
        String uniquePhone = "09" + String.valueOf(timestamp).substring(5);

        // Tạo User và gán luôn vào deptName vừa tạo
        String payload = String.format("""
            {
                "fullName": "Nguyen Van A",
                "email": "%s",
                "phone": "%s",
                "dateOfBirth": "1995-01-01",
                "gender": "Nam",
                "status": true,
                "houseNumber": "10",
                "street": "Duy Tan",
                "city": "Ha Noi",
                "commune": "Dich Vong",
                "district": "Cau Giay",
                "state": "Ha Noi",
                "postalCode": "10000",
                "country": "Vietnam",
                "positionName": "Trưởng phòng kế toán", 
                "departmentName": "%s" 
            }
        """, uniqueEmail, uniquePhone, deptName);

        System.out.println(">>> 2. Creating User inside Dept...");

        userId = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/users")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .extract().path("result.id");

        System.out.println(">>> Created User ID: " + userId);
    }

    // ============================================================
    // BƯỚC 3: ĐUỔI USER RA KHỎI PHÒNG (Test chức năng Remove)
    // ============================================================
    @Test
    @Order(4)
    @DisplayName("Step 3: Xóa nhân viên khỏi phòng ban")
    public void step3_RemoveUserFromDepartment() {
        System.out.println(">>> 3. Kicking User out of Dept...");

        String payload = String.format("""
        {
            "departmentId": %d,
            "userIds": [%d]
        }
    """, deptId, userId);

        given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/departments/deleteEmployees")
                .then()
                .log().ifValidationFails()
                .statusCode(200);
    }

    // ============================================================
    // BƯỚC 4: THÊM LẠI USER VÀO PHÒNG (Test chức năng Add)
    // ============================================================
    @Test
    @Order(5)
    @DisplayName("Step 4: Thêm lại nhân viên vào phòng ban")
    public void step4_AddUserToDepartment() {
        System.out.println(">>> 4. Adding User back to Dept...");

        String payload = String.format("""
            {
                "departmentId": %d,
                "userIds": [%d]
            }
        """, deptId, userId);

        given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/departments/" + deptId + "/employees")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("message", containsString("successfully"));
    }

    // ============================================================
    // BƯỚC 4.1: TEST THÊM USER KHÔNG TỒN TẠI (Negative Case)
    // ============================================================
    @Test
    @Order(6)
    @DisplayName("Validation: Thêm User ID không tồn tại vào phòng (Phải lỗi)")
    public void step4_1_AddInvalidUser() {
        int fakeUserId = 999999;
        String payload = String.format("""
            {
                "departmentId": %d,
                "userIds": [%d]
            }
        """, deptId, fakeUserId);

        given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/departments/" + deptId + "/employees")
                .then()
                .statusCode(400); // Expect Not Found
    }

    // ============================================================
    // BƯỚC 5: THĂNG CHỨC (Test chức năng Add Manager)
    // ============================================================
    @Test
    @Order(7)
    @DisplayName("Step 5: Thăng chức nhân viên lên Manager")
    public void step5_PromoteToManager() {
        System.out.println(">>> 5. Promoting User to Manager...");

        given()
                .header("Authorization", "Bearer " + adminToken)
                .when()
                .post("/departments/" + deptId + "/manager/" + userId)
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("message", containsString("Manager added"));
    }

    // ============================================================
    // BƯỚC 6: GIÁNG CHỨC (Test chức năng Remove Manager)
    // ============================================================
    @Test
    @Order(8)
    @DisplayName("Step 6: Hủy chức Manager của nhân viên")
    public void step6_RemoveManager() {
        System.out.println(">>> 6. Removing Manager...");

        given()
                .header("Authorization", "Bearer " + adminToken)
                .when()
                .delete("/departments/" + deptId + "/manager")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("message", containsString("removed"));
    }

    // ============================================================
    // BƯỚC 7: DỌN DẸP (Trả lại môi trường sạch)
    // ============================================================

}