package com.example.datn_team_ae.Service;

import com.example.datn_team_ae.DTO.SanPhamTuVanDTO;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.text.StringEscapeUtils; // Đảm bảo đã thêm dependency vào pom.xml

@Service
public class GeminiService {

    private static final Logger logger = Logger.getLogger(GeminiService.class.getName());

    @Value("AIzaSyCTU7iKs9w73u2UJjBP6HQzVIPrAU8L9Kc")
    private String API_KEY;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    // Cấu hình Retry
    private static final int MAX_RETRIES = 3; // Số lần thử lại tối đa
    private static final long INITIAL_RETRY_DELAY_MS = 500; // Độ trễ ban đầu: 0.5 giây

    public String hoiGemini(String cauHoi, List<SanPhamTuVanDTO> danhSach) throws Exception {

        StringBuilder promptBuilder = new StringBuilder();
    //prompt cho ai gemini
        promptBuilder.append("Bạn là nhân viên tư vấn thời trang cho cửa hàng MinLiNa. ")
                .append("Nhiệm vụ của bạn là tư vấn các sản phẩm thời trang và quần áo CÓ SẴN trong kho của MinLiNa. ")
                .append("Hãy trả lời một cách NGẮN GỌN, SÚC TÍCH, TRỰC TIẾP và có ĐỊNH DẠNG DỄ ĐỌC. ")
                .append("Khi liệt kê sản phẩm hoặc thông tin chi tiết, hãy sử dụng DẤU GẠCH ĐẦU DÒNG (-) hoặc NGẮT DÒNG rõ ràng. ")
                .append("Không trả lời quá 3-4 câu chính, nhưng thông tin sản phẩm có thể được liệt kê. ")
                .append("Chỉ tập trung vào thời trang, quần áo, sản phẩm cửa hàng, hoặc các vấn đề mua sắm liên quan. ")
                .append("Nếu câu hỏi KHÔNG LIÊN QUAN đến thời trang hoặc sản phẩm của MinLiNa, hãy LỊCH SỰ TỪ CHỐI và yêu cầu khách hàng hỏi về thời trang.\n\n");

        promptBuilder.append("Khách hàng hỏi: \"")
                .append(cauHoi).append("\".\n");

        promptBuilder.append("Dưới đây là thông tin về các sản phẩm hiện có trong kho của MinLiNa:\n");

        if (danhSach == null || danhSach.isEmpty()) {
            promptBuilder.append("- Hiện không có thông tin chi tiết về sản phẩm cụ thể nào được cung cấp. Hãy trả lời dựa trên kiến thức thời trang chung hoặc thông tin đã biết về MinLiNa nếu có, nhưng không đề cập sản phẩm cụ thể.\n");
        } else {

            for (SanPhamTuVanDTO sp : danhSach) {
                promptBuilder.append("- ")
                        .append(sp.getTenSanPham()).append(", màu: ")
                        .append(sp.getMauSac()).append(", size: ")
                        .append(sp.getKichThuoc()).append(", còn lại: ")
                        .append(sp.getSoLuong()).append(" sản phẩm.\n"); 
            }
        }
        promptBuilder.append("\nKết thúc câu trả lời của bạn một cách thân thiện và mời khách hàng tiếp tục đặt câu hỏi về thời trang.\n");
        String fullPrompt = promptBuilder.toString();

        String escapedPrompt = StringEscapeUtils.escapeJson(fullPrompt);

        logger.info("Prompt gốc: " + fullPrompt);
        logger.info("Prompt đã được escape JSON: " + escapedPrompt);

        String requestBody = """
        {
          "contents": [
            {
              "parts": [
                {
                  "text": "%s"
                }
              ]
            }
          ]
        }
        """.formatted(escapedPrompt);

        logger.info("JSON Request Body gửi đi: " + requestBody);

        for (int retryCount = 0; retryCount < MAX_RETRIES; retryCount++) {
            HttpResponse<String> response = null;
            int statusCode = -1;
            String responseBody = "";

            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://generativelanguage.googleapis.com/v1/models/gemini-2.0-flash:generateContent?key=" + API_KEY))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                        .build();

                response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                responseBody = response.body();
                statusCode = response.statusCode();

                logger.info("Phản hồi từ Gemini API (Status: " + statusCode + ", Lần thử: " + (retryCount + 1) + "): " + responseBody);

                if (statusCode == 200) {
                    JSONObject json;
                    try {
                        json = new JSONObject(responseBody);
                    } catch (JSONException e) {
                        logger.severe("Check Loi test: " + e.getMessage() + ". Phản hồi: " + responseBody);
                        throw new Exception("loi phan hoi tu gemini", e);
                    }

                    if (json.has("error")) {
                        JSONObject error = json.getJSONObject("error");
                        String errorMessage = "gemini 200: " + error.optString("message", "check loi ko xac dinh.");
                        logger.warning(errorMessage);
                        throw new Exception(errorMessage);
                    }

                    if (json.has("promptFeedback")) {
                        JSONObject promptFeedback = json.getJSONObject("promptFeedback");
                        if (promptFeedback.has("blockReason")) {
                            String blockReason = promptFeedback.optString("blockReason", "UNKNOWN");
                            String safetyRatingsInfo = promptFeedback.optString("safetyRatings", "N/A");
                            String errorMessage = "Câu hỏi bị chặn bởi bộ lọc an toàn của Gemini. Lý do: " + blockReason + ", Xếp hạng an toàn: " + safetyRatingsInfo;
                            logger.warning(errorMessage);
                            return "Xin lỗi Bạn, câu hỏi của bạn không thể được xử lý do nội dung không phù hợp với các chính sách an toàn.";
                        }
                    }

                    JSONArray candidates = json.optJSONArray("candidates");

                    if (candidates != null && candidates.length() > 0) {
                        try {
                            return candidates
                                    .getJSONObject(0)
                                    .getJSONObject("content")
                                    .getJSONArray("parts")
                                    .getJSONObject(0)
                                    .getString("text");
                        } catch (JSONException e) {
                            logger.severe("loi tu khoa json: " + e.getMessage() + ". Phản hồi: " + responseBody);
                            throw new Exception("gemini tra ve cau truc sai.", e);
                        }
                    } else {
                        String warningMessage = "gemini khong tra ve'" + cauHoi + "'. " + responseBody;
                        logger.warning(warningMessage);
                        return "Xin lỗi Bạn, tôi không thể tìm thấy thông tin phù hợp để trả lời câu hỏi của bạn lúc này. Bạn có muốn thử hỏi lại câu khác không?"; // Không retry cho lỗi này
                    }
                }

                else if (statusCode == 503 || statusCode == 429 ) {
                    long currentDelay = INITIAL_RETRY_DELAY_MS * (long) Math.pow(2, retryCount);
                    logger.warning("Gemini API trả về lỗi " + statusCode + " (retryable). Thử lại sau " + currentDelay + "ms. Lần thử: " + (retryCount + 1));
                    Thread.sleep(currentDelay);

                } else {
                    String errorMessage = "Lỗi từ Gemini API (HTTP Status: " + statusCode + "). ";
                    try {
                        JSONObject errorJson = new JSONObject(responseBody);
                        if (errorJson.has("error")) {
                            JSONObject error = errorJson.getJSONObject("error");
                            errorMessage += "Mã lỗi: " + error.optInt("code") + ", Thông báo: " + error.optString("message", "Không có thông báo lỗi chi tiết.");
                        } else {
                            errorMessage += "phan hoi ko phai dang json: " + responseBody;
                        }
                    } catch (JSONException jsonParseError) {
                        errorMessage += "json error: " + responseBody;
                    }
                    logger.severe(errorMessage);
                    throw new Exception(errorMessage);
                }

            } catch (java.io.IOException e) {
                long currentDelay = INITIAL_RETRY_DELAY_MS * (long) Math.pow(2, retryCount);
                logger.warning("gui http sai cau truc: " + e.getMessage() + ". Thử lại sau " + currentDelay + "ms. Lần thử: " + (retryCount + 1));
                Thread.sleep(currentDelay);
            } catch (InterruptedException e) {
                logger.severe("luong key api bi ngat.");
                Thread.currentThread().interrupt();
                throw new Exception("ket noi toi gemini that bai.", e);
            }
        }

        String finalErrorMessage = "Đã thử lại " + MAX_RETRIES + " gemini dang trong qua trinh ket noi lai  .";
        logger.severe(finalErrorMessage);
        throw new Exception(finalErrorMessage);
    }
}