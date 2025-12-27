package com.example.Humosoft;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Kiểm thử Chức năng Quản lý Nghỉ phép (Time Off)")
public class TimeOffTest {

    private static String adminToken;    // Token Admin (để duyệt)
    private static String employeeToken; // Token Nhân viên (để tạo)

    // Biến lưu ID đơn nghỉ phép (Lấy từ bước 2)
    private static int createdRequestId;

    // CẤU HÌNH DỮ LIỆU GIẢ LẬP (BẠN CẦN CHỈNH CHO KHỚP DB CỦA BẠN)
    private static final int TEST_USER_ID = 6;      // ID nhân viên thực trong DB
    private static final int TEST_LEAVE_TYPE_ID = 1; // ID loại nghỉ (ví dụ: 1 là Nghỉ phép năm)

    @BeforeAll
    @DisplayName("Cấu hình & Đăng nhập lấy Token")
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080";

        // 1. Login Admin
        String adminPayload = "{ \"username\": \"admin\", \"password\": \"admin123\" }";
        adminToken = given().contentType(ContentType.JSON).body(adminPayload)
                .post("/auth/signin").then().statusCode(200).extract().path("result");

        // 2. Login User
        String userPayload = "{ \"username\": \"nguyenvanc\", \"password\": \"10112004\" }";
        employeeToken = given().contentType(ContentType.JSON).body(userPayload)
                .post("/auth/signin").then().statusCode(200).extract().path("result");
    }

    // ============================================================
    // CASE TO_11: TẠO ĐƠN THÀNH CÔNG (Happy Path)
    // ============================================================
    @Test
    @Order(1)
    @DisplayName("TO_11: Tạo đơn nghỉ phép hợp lệ (Happy Path)")
    public void testCreateTimeOff_Success() {
        System.out.println(">>> 1. Creating Time Off Request...");

        // Ngày bắt đầu: Ngày mai, Ngày kết thúc: Ngày kia
        String startDate = LocalDate.now().plusDays(1).toString();
        String endDate = LocalDate.now().plusDays(2).toString();

        // Payload khớp với DTO TimeOffRequest của bạn
        String payload = String.format("""
            {
                "userId": %d,
                "leaveTypeId": %d, 
                "startDate": "%s",
                "endDate": "%s",
                "reason": "Test Create Success TO_11"
            }
        """, TEST_USER_ID, TEST_LEAVE_TYPE_ID, startDate, endDate);

        given()
                .header("Authorization", "Bearer " + employeeToken)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/timeoff")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("result", equalTo("Đơn nghỉ phép đã được gửi!"));
    }

    // ============================================================
    // BƯỚC PHỤ: LẤY ID ĐƠN VỪA TẠO (Do API Create không trả về ID)
    // ============================================================
    @Test
    @Order(2)
    @DisplayName("Bước phụ: Lấy ID đơn vừa tạo từ danh sách")
    public void fetchCreatedRequestId() {
        System.out.println(">>> 2. Fetching ID of created request...");

        List<Integer> ids = given()
                .header("Authorization", "Bearer " + adminToken)
                .when()
                .get("/timeoff")
                .then()
                .statusCode(200)
                .extract().path("result.id"); // Lấy danh sách ID từ response

        if (!ids.isEmpty()) {
            // Lấy phần tử cuối cùng (giả định là cái mới nhất)
            createdRequestId = ids.get(ids.size() - 1);
            System.out.println(">>> Found Request ID: " + createdRequestId);
        } else {
            Assertions.fail("List TimeOff is empty! Test Step 1 failed implicitly.");
        }
    }

    // ============================================================
    // CASE TO_07: PHÊ DUYỆT ĐƠN (Happy Path)
    // ============================================================
    @Test
    @Order(3)
    @DisplayName("TO_07: Admin phê duyệt đơn nghỉ phép")
    public void testApproveRequest() {
        System.out.println(">>> 3. Approving Request ID: " + createdRequestId);

        given()
                .header("Authorization", "Bearer " + adminToken)
                .when()
                .put("/timeoff/" + createdRequestId + "/approve")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("result", containsString("phê duyệt"));
    }

    // ============================================================
    // CASE TO_04: NGÀY KẾT THÚC TRƯỚC NGÀY BẮT ĐẦU (Validation)
    // ============================================================
    @Test
    @Order(4)
    @DisplayName("TO_04: Lỗi Validation - Ngày kết thúc trước ngày bắt đầu")
    public void testCreate_Fail_InvalidDates() {
        System.out.println(">>> 4. Testing Date Logic (End < Start)...");

        String startDate = LocalDate.now().plusDays(5).toString();
        String endDate = LocalDate.now().plusDays(3).toString(); // Sai logic

        String payload = String.format("""
            {
                "userId": %d,
                "leaveTypeId": %d,
                "startDate": "%s",
                "endDate": "%s",
                "reason": "Invalid Dates"
            }
        """, TEST_USER_ID, TEST_LEAVE_TYPE_ID, startDate, endDate);

        given()
                .header("Authorization", "Bearer " + employeeToken)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/timeoff")
                .then()
                .log().ifValidationFails()
                .statusCode(400); // Expect Bad Request
    }

    // ============================================================
    // CASE TO_08: TRÙNG LỊCH (Overlapping)
    // ============================================================
    @Test
    @Order(5)
    @DisplayName("TO_08: Lỗi Logic - Đăng ký trùng lịch đã có")
    public void testCreate_Fail_Overlapping() {
        System.out.println(">>> 5. Testing Overlapping...");

        // Tạo lại đơn trùng ngày với Step 1
        String startDate = LocalDate.now().plusDays(1).toString();
        String endDate = LocalDate.now().plusDays(2).toString();

        String payload = String.format("""
            {
                "userId": %d,
                "leaveTypeId": %d,
                "startDate": "%s",
                "endDate": "%s",
                "reason": "Overlapping Test"
            }
        """, TEST_USER_ID, TEST_LEAVE_TYPE_ID, startDate, endDate);

        given()
                .header("Authorization", "Bearer " + employeeToken)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/timeoff")
                .then()
                .log().ifValidationFails()
                .statusCode(400); // Expect Bad Request
    }

    // ============================================================
    // CASE TO_09: KIỂM TRA SỐ DƯ PHÉP (Leave Balance)
    // ============================================================
    @Test
    @Order(6)
    @DisplayName("TO_09: Lỗi Logic - Không đủ số dư ngày nghỉ")
    public void testCreate_Fail_InsufficientBalance() {
        System.out.println(">>> 6. Testing Balance...");

        // Xin nghỉ 100 ngày
        String startDate = LocalDate.now().plusDays(10).toString();
        String endDate = LocalDate.now().plusDays(110).toString();

        String payload = String.format("""
            {
                "userId": %d,
                "leaveTypeId": %d,
                "startDate": "%s",
                "endDate": "%s",
                "reason": "Too many days"
            }
        """, TEST_USER_ID, TEST_LEAVE_TYPE_ID, startDate, endDate);

        given()
                .header("Authorization", "Bearer " + employeeToken)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/timeoff")
                .then()
                .log().ifValidationFails()
                .statusCode(400); // Expect Bad Request
    }

    @Test
    @Order(7)
    @DisplayName("TO_00: Lỗi Validation - Thiếu trường bắt buộc")
    public void testCreate_Fail_MissingFields() {
        System.out.println(">>> 7. Testing Missing Fields...");

        // Payload thiếu leaveTypeId và startDate
        String payload = String.format("""
            {
                "userId": %d,
                "reason": "Missing Fields Test"
            }
        """, TEST_USER_ID);

        given()
                .header("Authorization", "Bearer " + employeeToken)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/timeoff")
                .then()
                .log().ifValidationFails()
                .statusCode(400); // Backend phải chặn lại
    }

    // ============================================================
    // CASE TO_05: NGÀY BẮT ĐẦU TRONG QUÁ KHỨ
    // ============================================================
    @Test
    @Order(8)
    @DisplayName("TO_05: Lỗi Validation - Ngày bắt đầu trong quá khứ")
    public void testCreate_Fail_PastDate() {
        System.out.println(">>> 8. Testing Past Date...");

        // Lùi lại 5 ngày trước
        String startDate = LocalDate.now().minusDays(5).toString();
        String endDate = LocalDate.now().minusDays(2).toString();

        String payload = String.format("""
            {
                "userId": %d,
                "leaveTypeId": %d,
                "startDate": "%s",
                "endDate": "%s",
                "reason": "Past Date Test"
            }
        """, TEST_USER_ID, TEST_LEAVE_TYPE_ID, startDate, endDate);

        given()
                .header("Authorization", "Bearer " + employeeToken)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/timeoff")
                .then()
                .log().ifValidationFails()
                .statusCode(400); // Nếu quy định cấm nghỉ quá khứ
    }

    // ============================================================
    // CASE TO_10: REASON QUÁ DÀI
    // ============================================================
    @Test
    @Order(9)
    @DisplayName("TO_10: Lỗi Validation - Lý do quá dài")
    public void testCreate_Fail_LongReason() {
        System.out.println(">>> 9. Testing Long Reason...");

        String longReason = "A".repeat(300); // Giả sử DB giới hạn 255 ký tự

        String payload = String.format("""
            {
                "userId": %d,
                "leaveTypeId": %d,
                "startDate": "%s",
                "endDate": "%s",
                "reason": "%s"
            }
        """, TEST_USER_ID, TEST_LEAVE_TYPE_ID, LocalDate.now().plusDays(20).toString(), LocalDate.now().plusDays(21).toString(), longReason);

        given()
                .header("Authorization", "Bearer " + employeeToken)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/timeoff")
                .then()
                .statusCode(400);
    }

}