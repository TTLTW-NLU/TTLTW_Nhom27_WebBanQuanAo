<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chính Sách Đổi Trả</title>
    <!-- Favicon -->
    <link rel="shortcut icon" href="assets/imgs/admin/favicon-32x32.png" type="image/png">
    <!-- Link CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css"
        integrity="sha512-NhSC1YmyruXifcj/KFRWoC561YpHpc5Jtzgvbuzx5VozKpWvQ+4nXhPdFgmx8xqexRcpAglTj9sIBWINXa8x5w=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"
        integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" href="./assets/bootstrap-4.6.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="./assets/css/base.css">
    <link rel="stylesheet" href="./assets/css/return-policy.css">
    <link rel="stylesheet" href="./assets/css/header-footer.css">

</head>

<body>
    <header id="header"><%@ include file="assets/includes/header.jsp" %></header>

    <div class="white-space" style="height: 15rem;"></div>

    <section class="policy-section">
        <div class="grid">
            <h1>Chính Sách Đổi Trả</h1>
            <p>Chúng tôi cam kết mang đến trải nghiệm mua sắm tuyệt vời cho khách hàng. Dưới đây là chính sách đổi trả
                của chúng tôi:</p>

            <ul>
                <li><strong>1. Điều kiện đổi trả:</strong> Sản phẩm phải còn nguyên vẹn, không bị hư hại, không qua sử
                    dụng, và có đầy đủ bao bì, hóa đơn.</li>
                <li><strong>2. Thời gian đổi trả:</strong> Quý khách có thể đổi trả sản phẩm trong vòng 30 ngày kể từ
                    ngày nhận hàng.</li>
                <li><strong>3. Lý do đổi trả:</strong> Sản phẩm bị lỗi, sai mẫu mã, hoặc không đúng với mô tả sản phẩm
                    trên website.</li>
                <li><strong>4. Phương thức hoàn tiền:</strong> Hoàn tiền qua phương thức thanh toán ban đầu hoặc chuyển
                    khoản ngân hàng, tùy theo yêu cầu của khách hàng.</li>
                <li><strong>5. Chi phí vận chuyển:</strong> Nếu sản phẩm bị lỗi, chúng tôi sẽ chịu trách nhiệm vận
                    chuyển lại sản phẩm miễn phí. Nếu khách hàng đổi trả vì lý do cá nhân, chi phí vận chuyển sẽ do
                    khách hàng chịu.</li>
            </ul>

            <div class="policy-button">
                <a href="contact.jsp"> <button>Liên Hệ Chúng Tôi</button></a>
            </div>
        </div>
    </section>

    <section class="faq-section">
        <div class="grid">
            <h2>Các Câu Hỏi Thường Gặp (FAQ)</h2>
            <div class="faq-item">
                <button class="faq-toggle">Làm sao để yêu cầu đổi trả sản phẩm?</button>
                <div class="faq-answer">
                    <p>Bạn chỉ cần liên hệ với bộ phận hỗ trợ khách hàng qua số điện thoại hoặc email của chúng tôi,
                        cung cấp thông tin đơn hàng và lý do đổi trả. Chúng tôi sẽ hướng dẫn bạn quy trình hoàn tất thủ
                        tục đổi trả.</p>
                </div>
            </div>

            <div class="faq-item">
                <button class="faq-toggle">Tôi có thể đổi trả sản phẩm nếu đã mở hộp không?</button>
                <div class="faq-answer">
                    <p>Có, nếu sản phẩm không bị hư hại, không qua sử dụng và còn đầy đủ bao bì gốc. Chúng tôi chỉ chấp
                        nhận đổi trả khi sản phẩm không bị ảnh hưởng về chất lượng.</p>
                </div>
            </div>

            <div class="faq-item">
                <button class="faq-toggle">Chính sách hoàn tiền của các sản phẩm giảm giá như thế nào?</button>
                <div class="faq-answer">
                    <p>Sản phẩm giảm giá vẫn áp dụng chính sách đổi trả như các sản phẩm khác. Tuy nhiên, chúng tôi chỉ
                        hoàn tiền qua phương thức thanh toán ban đầu và không chấp nhận hoàn tiền bằng tiền mặt cho các
                        sản phẩm này.</p>
                </div>
            </div>

            <div class="faq-item">
                <button class="faq-toggle">Tôi có thể đổi trả sản phẩm tại cửa hàng không?</button>
                <div class="faq-answer">
                    <p>Có, bạn có thể đến cửa hàng của chúng tôi để yêu cầu đổi trả sản phẩm trực tiếp, chỉ cần mang
                        theo hóa đơn và sản phẩm.</p>
                </div>
            </div>
        </div>
    </section>

    <footer id="footer"><%@ include file="assets/includes/footer.jsp" %></footer>

    <%@ include file="assets/includes/foot.jsp" %>

    <script>
        // Toggle FAQ answers
        document.querySelectorAll('.faq-toggle').forEach(button => {
            button.addEventListener('click', () => {
                const answer = button.nextElementSibling;
                answer.classList.toggle('open');
            });
        });
    </script>
</body>

</html>