document.addEventListener("DOMContentLoaded", function () {
    // Hiển thị phần tử main đầu tiên khi tải trang
    const dashboard = document.getElementById("admin/manager-orders");
    if (dashboard) {
        dashboard.classList.add("block"); // Hiển thị quản lý đơn hàng
        fetchOrders(); // Gọi fetchOrders chỉ một lần khi tải trang
    }

    // Ẩn các phần tử main khác nếu có
    document.querySelectorAll("main").forEach((main) => {
        if (main.id !== "admin/manager-orders") {
            main.classList.remove("block");
        }
    });
});

function showMain(event, mainId) {
    event.preventDefault();

    // Bỏ active khỏi menu
    document.querySelectorAll("aside .sidebar a").forEach((link) => {
        link.classList.remove("active");
    });
    event.currentTarget.classList.add("active");

    // Ẩn tất cả main
    document.querySelectorAll("main").forEach((main) => {
        main.classList.remove("block");
    });

    // Hiện đúng main
    const targetMain = document.getElementById(mainId);
    if (targetMain) {
        targetMain.classList.add("block");
    }

    // Gọi AJAX tương ứng với ID
    if (mainId === 'admin/manager-orders') {
        // Phát sự kiện để fetch dữ liệu đơn hàng
        const event = new CustomEvent('loadOrders', { detail: targetMain });
        window.dispatchEvent(event);
    } else if (mainId === 'admin/manager-users') {
        fetchUsers();
    } else if (mainId === 'admin/manager-products') {
        fetchProducts();
    }
}

// Document operation functions
const sideMenu = document.querySelector("aside");
const menuBtn = document.querySelector("#menu-btn");
const closeBtn = document.querySelector("#close-btn");
const themeToggler = document.querySelector(".theme-toggler");

// Show Sidebar
menuBtn.addEventListener("click", () => {
    sideMenu.style.display = "block";
});

// Hide Sidebar
closeBtn.addEventListener("click", () => {
    sideMenu.style.display = "none";
});

// Change Theme
themeToggler.addEventListener("click", () => {
    document.body.classList.toggle("dark-theme-variables");

    themeToggler.querySelector("span:nth-child(1)").classList.toggle("active");
    themeToggler.querySelector("span:nth-child(2)").classList.toggle("active");
});


//========== start Xu ly su kien cho order detail ============//
function hideOverlay(event) {
    const overlay = event.target.closest("main").querySelector(".overlay");
    overlay.style.display = "none"; // Ẩn overlay khi nhấn vào nó
}

function hideOverlayProductDetails(event) {
    const overlay = event.target.closest("main").querySelector(".overlay-productDetails");
    overlay.style.display = "none"; // Ẩn overlay khi nhấn vào nó
}

function hideOverlayOrderItemDetails(event) {
    const overlay = event.target.closest("main").querySelector(".overlay-orderItemDetails");
    overlay.style.display = "none"; // Ẩn overlay khi nhấn vào nó
}

function hideOverlayuserRolePermissons(event) {
    const overlay = event.target.closest("main").querySelector(".overlay-userRolePermissons");
    overlay.style.display = "none"; // Ẩn overlay khi nhấn vào nó
}

function showOverlay(event) {
    const main = event.target.closest("main");
    const overlay = main.querySelector(".overlay");
    overlay.style.display = "block"; // Hiển thị overlay của main hiện tại
}

//========== end Xu ly su kien cho order detail ============//


