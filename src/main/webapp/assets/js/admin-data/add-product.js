const PRODUCTS_DATA = [];

document.getElementById("add-product-form").addEventListener("submit", function(event) {
  event.preventDefault(); // Ngăn form gửi yêu cầu mặc định

  // Lấy giá trị từ form
  const productName = document.getElementById("product-name").value;
  const productPrice = document.getElementById("product-price").value + " VND";
  const productPayment = document.getElementById("product-payment").value; // Lấy phương thức thanh toán
  const productStatus = document.getElementById("product-status").value; // Lấy trạng thái
  const productMaterial = document.getElementById("product-material").value;
  const productAmount = document.getElementById("product-amount").value;
  const productStockInDate = document.getElementById("product-stock-in-date").value;
  const productImage = document.getElementById("product-image").value || "https://via.placeholder.com/150";

  // Tạo đối tượng sản phẩm mới
  const newProduct = {
    proId: `P${PRODUCTS_DATA.length + 1}`,
    proName: productName,
    proPrice: productPrice,
    proPayment: productPayment, // Ghi lại phương thức thanh toán
    proStatus: productStatus,  // Ghi lại trạng thái
    proStatusColor: getStatusColor(productStatus), // Gán màu sắc cho trạng thái
    proMaterial: productMaterial,
    proAmount: productAmount,
    proStockInDate: productStockInDate,
    proImg: productImage, // Lưu đường dẫn ảnh
    proAction: "",
  };

  // Thêm sản phẩm vào mảng dữ liệu
  PRODUCTS_DATA.push(newProduct);

  // Hiển thị lại danh sách sản phẩm
  displayProducts();

  // Reset form
  document.getElementById("add-product-form").reset();
});

// Hàm xác định màu sắc cho trạng thái
function getStatusColor(status) {
  switch (status) {
    case "Đã Giao Hàng":
      return "success";
    case "Chờ Xử Lý":
      return "warning";
    case "Hủy Đơn":
      return "danger";
    default:
      return "primary";
  }
}

// Hàm hiển thị danh sách sản phẩm
function displayProducts() {
  const productList = document.getElementById("product-list");
  productList.innerHTML = PRODUCTS_DATA.map(product => `
    <div class="product-card">
      <img src="${product.proImg}" alt="${product.proName}">
      <h3>${product.proName} (${product.proId})</h3>
      <p><b>Giá:</b> ${product.proPrice}</p>
      <p><b>Phương Thức Thanh Toán:</b> ${product.proPayment}</p>
      <p><b>Trạng Thái:</b> <span class="${product.proStatusColor}">${product.proStatus}</span></p>
      <p><b>Chất Liệu:</b> ${product.proMaterial}</p>
      <p><b>Số Lượng:</b> ${product.proAmount}</p>
      <p><b>Ngày Nhập Kho:</b> ${product.proStockInDate}</p>
    </div>
  `).join("");
}
