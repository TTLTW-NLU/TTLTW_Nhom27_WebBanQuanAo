/*--------------------------------------------------------
---------------------------------------------------------

                       Manager OrderDetails

---------------------------------------------------------
----------------------------------------------------------*/
// Hàm mở chi tiết orderItem
function openOrderItemDetails(event) {
    const orderId = event.target.getAttribute("data-orderItemsId");

    const main = event.target.closest("main");
    const overlay = main.querySelector(".overlay-orderItemDetails");

    // Gửi yêu cầu AJAX để lấy dữ liệu chi tiết orderItem theo orderId
    $.ajax({
        url: `/WebBanQuanAo/admin/api/order-details/${orderId}`,
        type: 'GET',
        dataType: 'json',
        cache: false,
        success: function (response) {
            const data = response.data;
            if (data && data.length > 0) {
                let orderItemDetailListContent = '';

                // Duyệt qua tất cả các chi tiết orderItem và tạo HTML để hiển thị
                data.forEach(orderItem => {
                    orderItemDetailListContent += `
                    <tr>
                        <td><input type="number" value="${orderItem.id}" data-field="id" disabled></td>
                        <td><input type="number" value="${orderItem.orderId}" data-field="orderId" disabled></td>
                        <td><input type="text" value="${orderItem.productName}" data-field="productName" disabled></td>
                        <td><input type="text" value="${orderItem.color}" data-field="color" disabled></td>
                        <td><input type="text" value="${orderItem.size}" data-field="size" disabled></td>
                        <td><input type="number" value="${orderItem.quantity}" class="editable" data-id="${orderItem.id}" data-field="quantity"></td>
                        <td><input type="number" value="${orderItem.unitPrice}" class="editable" data-id="${orderItem.id}" data-field="unitPrice"></td>
                        <td><input type="number" value="${orderItem.discount}" class="editable" data-id="${orderItem.id}" data-field="discount"></td>
                        <td><span onclick="saveOrderItemEdits(event)" class="primary material-icons-sharp">save</span></td>
                    </tr>
                   `;

                });

                // Điền dữ liệu vào phần <tbody> của bảng
                const tableBody = document.querySelector("#orderItem-details--table tbody");
                tableBody.innerHTML = orderItemDetailListContent;

                // chỉ hiển thị popup nếu có dữ liệu
                overlay.style.display = "block";
            } else {
                alert("Đơn hàng ID = " + orderId + " không có chi tiết đơn hàng!");
            }
        },
        error: function (xhr, status, error) {
            console.error("Lỗi khi lấy dữ liệu chi tiết đơn hàng:", error);
            const message = xhr.responseJSON?.message || "Không thể lấy thông tin chi tiết đơn hàng. Vui lòng thử lại.";
            alert(message);
        }
    });
}

// Hàm lưu chỉnh sửa chi tiết orderItem
function saveOrderItemEdits(event) {
    event.preventDefault();

    // Lấy hàng chứa nút "save" được nhấn
    const row = event.target.closest("tr");

    // Lấy các giá trị từ các trường trong hàng
    const orderItemDetail = {
        id: parseInt(row.querySelector("input[data-field='id']").value),
        orderId: parseInt(row.querySelector("input[data-field='orderId']").value),
        productName: row.querySelector("input[data-field='productName']").value,
        color: row.querySelector("input[data-field='color']").value,
        size: row.querySelector("input[data-field='size']").value,
        quantity: parseInt(row.querySelector("input[data-field='quantity']").value),
        unitPrice: parseFloat(row.querySelector("input[data-field='unitPrice']").value),
        discount: parseFloat(row.querySelector("input[data-field='discount']").value)
    };

    // Kiểm tra các giá trị bắt buộc
    if (orderItemDetail.quantity <= 0 || orderItemDetail.unitPrice <= 0) {
        alert("Vui lòng nhập số lượng và đơn giá hợp lệ.");
        return;
    }

    // Gửi yêu cầu AJAX để lưu chi tiết orderItem
    $.ajax({
        url: `/WebBanQuanAo/admin/api/order-details/${orderItemDetail.id}`, // Đảm bảo đường dẫn chính xác
        type: 'PUT',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(orderItemDetail),
        success: function (response) {
            alert(response.message || "Cập nhật chi tiết đơn hàng thành công!");
        },
        error: function (xhr, status, error) {
            console.error("Lỗi khi cập nhật chi tiết đơn hàng:", error);
            console.error("Chi tiết lỗi:", xhr.responseText);
            const message = xhr.responseJSON?.message || "Không thể cập nhật chi tiết đơn hàng. Vui lòng thử lại.";
            alert(message);
        }
    });
}


