/*--------------------------------------------------------
---------------------------------------------------------

                       Manager Product

---------------------------------------------------------
----------------------------------------------------------*/

//---------------Product Data--------------------//
// Lấy danh sách sản phẩm từ server
function fetchProducts() {
    $.ajax({
        url: '/WebBanQuanAo/admin/api/products',
        type: 'GET',
        dataType: 'json',
        success: function (response) {
            const products = response.data;
            const table = $("#products--table");

            if ($.fn.DataTable.isDataTable(table)) {
                table.DataTable().destroy();
                table.find("tbody").empty();
            }

            const tbody = buildTableProduct(products);
            table.append(tbody);

            table.DataTable({
                searching: true,
                info: true,
                order: [[0, 'asc']],
                language: {
                    url: '//cdn.datatables.net/plug-ins/1.13.5/i18n/vi.json'
                },
                paging: true,
                pageLength: 5,
                lengthChange: true,
            });
        },
        error: function (xhr, status, error) {
            console.error('Lỗi khi lấy danh sách sản phẩm:', error);
            alert("Không thể lấy danh sách sản phẩm. Vui lòng thử lại sau.");
        }
    });
}

// Tạo bảng từ danh sách sản phẩm
const buildTableProduct = (products) => {
    const tbody = document.createElement("tbody");

    let productContent = "";
    for (const product of products) {
        let typeIdOptionsForId = `
            <select disabled>
                <option value="1" ${product.typeId === 1 ? 'selected' : ''}>Áo</option>
                <option value="2" ${product.typeId === 2 ? 'selected' : ''}>Quần</option>
            </select>
        `;

        let supplierOptionsForId = `
            <select disabled>
                <option value="1" ${product.supplierId === 1 ? 'selected' : ''}>PEALO</option>
                <option value="2" ${product.supplierId === 2 ? 'selected' : ''}>B Brown Studio</option>
                <option value="3" ${product.supplierId === 3 ? 'selected' : ''}>BBRAND</option>
                <option value="4" ${product.supplierId === 4 ? 'selected' : ''}>RUYCH STUDIO</option>
            </select>
        `;

        let categoryOptionsForId = `
            <select disabled>
                <option value="1" ${product.categoryId === 1 ? 'selected' : ''}>Áo nam</option>
                <option value="2" ${product.categoryId === 2 ? 'selected' : ''}>Áo Nữ</option>
                <option value="3" ${product.categoryId === 3 ? 'selected' : ''}>Áo trẻ em</option>
                <option value="4" ${product.categoryId === 4 ? 'selected' : ''}>Quần nam</option>
                <option value="5" ${product.categoryId === 5 ? 'selected' : ''}>Quần nữ</option>
                <option value="6" ${product.categoryId === 6 ? 'selected' : ''}>Quần trẻ em</option>
            </select>
        `;

        let statusText = "";
        switch (product.status) {
            case 0: statusText = "Đã xóa"; break;
            case 1: statusText = "Còn"; break;
            case 2: statusText = "Hết"; break;
        }

        productContent += `
        <tr>
          <td>${product.id}</td>
          <td>${typeIdOptionsForId}</td>
          <td>${categoryOptionsForId}</td>
          <td>${supplierOptionsForId}</td>
          <td>${product.name}</td>
          <td>${product.description}</td>
          <td>${product.releaseDate}</td>
          <td>${product.unitSold}</td>
          <td>${product.unitPrice}</td>
          <td>${statusText}</td>
          <td class="primary">
            <span onclick="openEditProductPopup(event)" class="material-icons-sharp" data-product-id="${product.id}"> edit </span>
            <span onclick="deleteProduct(event)" class="material-icons-sharp" data-product-id="${product.id}"> delete </span>
            <span onclick="openProductDetails(event)" class="material-icons-sharp" data-productId-details="${product.id}"> info </span>
          </td>
        </tr>
      `;
    }

    tbody.innerHTML = productContent;

    return tbody;
};

// Khi DOM load, gọi fetchProducts chỉ khi phần tử 'admin/manager-products' cần hiển thị
document.addEventListener('DOMContentLoaded', function () {
    const productSection = document.getElementById("admin/manager-products");
    if (productSection && productSection.classList.contains("block")) {
        fetchProducts();
    }
});

// Hàm xóa sản phẩm
function deleteProduct(event) {
    const productId = parseInt(event.target.getAttribute("data-product-id"), 10); // Chuyển đổi thành int

    if (isNaN(productId)) {
        alert("ID sản phẩm không hợp lệ.");
        return;
    }

    if (confirm(`Bạn có chắc chắn muốn xóa sản phẩm ID: ${productId}?`)) {
        $.ajax({
            url: `/WebBanQuanAo/admin/api/products/${productId}`,
            type: 'DELETE',
            cache: false,
            success: function (response) {
                alert(response.message || "Đã xóa sản phẩm thành công.");
                fetchProducts();
            },
            error: function (xhr, status, error) {
                console.error('Lỗi khi xóa sản phẩm:', error);
                const message = xhr.responseJSON?.message || "Không thể xóa sản phẩm. Vui lòng thử lại sau.";
                alert(message);
            }
        });
    }
}


//---------------Edit sản phẩm--------------------//
function openEditProductPopup(event) {
    const productId = event.target.getAttribute("data-product-id");

    const main = event.target.closest("main");
    const overlay = main.querySelector(".overlay");
    overlay.style.display = "block";

    $.ajax({
        url: `/WebBanQuanAo/admin/api/products/${productId}`,
        type: 'GET',
        success: function (response) {
            const data = response.data;
            $("#edit-idProduct").val(data.id);
            $("#edit-typeId").val(data.typeId);
            $("#edit-categoryId").val(data.categoryId);
            $("#edit-supplierId").val(data.supplierId);
            $("#edit-name").val(data.name);
            $("#edit-description").val(data.description);

            // Format ngày yyyy-MM-dd
            const releaseDate = new Date(data.releaseDate);
            const formattedDate = releaseDate.toISOString().split('T')[0];
            $("#edit-releaseDate").val(formattedDate);

            $("#edit-unitSold").val(data.unitSold);
            $("#edit-unitPrice").val(data.unitPrice);
            $("#edit-statusProduct").val(data.status);
        },
        error: function (xhr, status, error) {
            console.error("Lỗi khi lấy dữ liệu sản phẩm:", error);
            const message = xhr.responseJSON?.message || "Không thể lấy thông tin sản phẩm. Vui lòng thử lại.";
            alert(message);
        }
    });
}

function saveProductEdits(event) {
    event.preventDefault();

    const releaseDateRaw = document.getElementById("edit-releaseDate").value;
    const releaseDate = releaseDateRaw.split('T')[0]; // Chỉ lấy phần ngày (yyyy-MM-dd)

    const product = {
        id: parseInt($("#edit-idProduct").val()),
        typeId: parseInt($("#edit-typeId").val()),
        categoryId: parseInt($("#edit-categoryId").val()),
        supplierId: parseInt($("#edit-supplierId").val()),
        name: $("#edit-name").val(),
        description: $("#edit-description").val(),
        releaseDate: releaseDate, // Gắn giá trị đã định dạng
        unitSold: parseInt($("#edit-unitSold").val()),
        unitPrice: parseFloat($("#edit-unitPrice").val()),
        status: parseInt($("#edit-statusProduct").val())
    };

    $.ajax({
        url: `/WebBanQuanAo/admin/api/products/${product.id}`, // Gửi id trên URL như orders
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(product),
        cache: false,
        success: function (response) {
            alert(response.message ||"Cập nhật thông tin sản phẩm thành công!");
            fetchProducts(); // Reload bảng sản phẩm
            hideOverlay(); // Ẩn popup
        },
        error: function (xhr, status, error) {
            console.error("Lỗi khi cập nhật sản phẩm:", error);
            console.log(JSON.stringify(product))
            const message = xhr.responseJSON?.message || "Không thể cập nhật sản phẩm. Lỗi không xác định từ server.";
            alert(message);
        }
    });
}

/*--------------------------------------------------------
---------------------------------------------------------

                       Add Product

---------------------------------------------------------
----------------------------------------------------------*/

// Hiển thị form thêm người dùng
function showAddProductForm() {
    const overlay = document.querySelector(".overlay-addProduct");
    overlay.style.display = "flex"; // Hiển thị lớp phủ
}


function hideAddProductForm() {
    const overlay = document.querySelector(".overlay-addProduct");
    const form = document.getElementById("add-product-form");
    form.reset(); // Xóa dữ liệu trong form
    overlay.style.display = "none";
}


function createProduct(event) {
    event.preventDefault();

    const form = document.getElementById('add-product-form');
    const formData = new FormData(form);

    const productData = Object.fromEntries(formData.entries());

    // Đảm bảo rằng releaseDate đúng định dạng
    productData.releaseDate = new Date(productData.releaseDate).toISOString().split('T')[0]; // yyyy-MM-dd

    // Chuyển các giá trị của các select thành số (parseInt)
    productData.typeId = parseInt(productData.typeId);
    productData.categoryId = parseInt(productData.categoryId);
    productData.supplierId = parseInt(productData.supplierId);
    productData.unitSold = parseInt(productData.unitSold);
    productData.unitPrice = parseFloat(productData.unitPrice);

    // Chuyển status từ chuỗi thành số
    productData.status = parseInt(productData.status);

    // Chuyển status thành text
    let statusText = "";
    switch (productData.status) {
        case 0:
            statusText = "Đã xóa";
            break;
        case 1:
            statusText = "Còn hàng";
            break;
        case 2:
            statusText = "Hết hàng";
            break;
        default:
            statusText = "Không xác định"; // Nếu không phải 0, 1, 2 thì gán mặc định
            break;
    }

    console.log("Status text:", statusText);
    console.log(JSON.stringify(productData));

    // Gửi dữ liệu qua fetch
    fetch('/WebBanQuanAo/admin/api/products', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(productData)
    })
        .then(response => {
            if (response.ok) return response.json();
            return response.json().then(err => Promise.reject(err));
        })
        .then(data => {
            alert(data.message || 'Sản phẩm đã được tạo thành công!');
            fetchProducts();
            hideAddProductForm();
        })
        .catch(err => {
            console.error('Lỗi:', err);
            alert(err.message || 'Không thể tạo sản phẩm. Vui lòng thử lại.');
        });
}

window.addEventListener("DOMContentLoaded", hideAddProductForm);

// Xử lý sự kiện click ra ngoài form để ẩn form
document.addEventListener("click", function(event) {
    const overlay = document.querySelector(".overlay-addProduct");
    const form = document.getElementById("add-product-form");

    // Kiểm tra nếu form đang hiển thị và click không nằm trong form hoặc nút hiển thị form
    if (overlay.style.display === "flex" &&
        !form.contains(event.target) &&
        !event.target.closest("button")) {
        hideAddProductForm();
    }
});

