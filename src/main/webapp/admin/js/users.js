// Lấy danh sách người dùng từ server và khởi tạo DataTables
function fetchUsers() {
    $.ajax({
        url: `/WebBanQuanAo/admin/api/users`,
        type: 'GET',
        dataType: 'json',
        success: function (response) {
            const users = response.data;
            const table = $("#users--table");

            // Xóa DataTables nếu đã được khởi tạo trước đó
            if ($.fn.DataTable.isDataTable(table)) {
                table.DataTable().destroy();
                table.find("tbody").empty();
            }

            // Thêm dữ liệu mới vào bảng
            const tbody = buildTableUser(users);
            table.append(tbody);

            // Khởi tạo lại DataTables với phân trang và tìm kiếm
            table.DataTable({
                searching: true,
                info: true,
                order: [[0, 'asc']],
                language: {
                    url: '//cdn.datatables.net/plug-ins/1.13.5/i18n/vi.json'
                },
                paging: true,
                pageLength: 3,
                lengthChange: true,
            });
        },
        error: function (xhr, status, error) {
            console.error('Error fetching users:', error);
            alert("Failed to fetch users. Please try again later.");
        }
    });
}

// Hàm định dạng ngày từ ISO sang dd-MM-yyyy
const formatDate = (isoString) => {
    if (!isoString) return "N/A";
    const date = new Date(isoString);
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const year = date.getFullYear();
    return `${day}-${month}-${year}`;
};

// Tạo tbody từ danh sách người dùng
const buildTableUser = (users) => {
    let userContent = "";
    for (const user of users) {
        let statusText = "";
        switch (user.status) {
            case 0: statusText = "Chưa kích hoạt"; break;
            case 1: statusText = "Hoạt động"; break;
            case 2: statusText = "Bị khóa"; break;
            case 3: statusText = "Bị cấm"; break;
            case 4: statusText = "Bị xóa"; break;
        }
        let phone = user.phone !== null ? user.phone : "N/A";

        userContent += `
            <tr>
                <td>${user.id}</td>
                <td>${user.userName}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.email}</td>
                <td>${user.avatar || ''}</td>
                <td>${user.address || ''}</td>
                <td>${phone}</td>
                <td>${formatDate(user.createdAt)}</td>
                <td>${statusText}</td>
                <td class="primary" style="width: 90px">
                    <span onclick="openEditPopup(event)" class="material-icons-sharp" data-username="${user.userName}">edit</span>
                    <span onclick="deleteUser(event)" class="material-icons-sharp" data-username="${user.userName}">delete</span>
                    <span onclick="openRolePermissionUser(event)" class="material-icons-sharp" data-username="${user.userName}">security</span>
                </td>
            </tr>
        `;
    }
    return `<tbody>${userContent}</tbody>`;
};

// Khi DOM load, gọi fetchProducts chỉ khi phần tử 'admin/manager-products' cần hiển thị
document.addEventListener('DOMContentLoaded', function () {
    const productSection = document.getElementById("admin/manager-users");
    if (productSection && productSection.classList.contains("block")) {
        fetchProducts();
    }
});

// Hàm xóa user
function deleteUser(event) {
    const userName = event.target.getAttribute("data-username");
    if (confirm(`Bạn có chắc chắn muốn xóa người dùng: ${userName}?`)) {
        $.ajax({
            url: `/WebBanQuanAo/admin/api/users/${encodeURIComponent(userName)}`,
            type: 'DELETE',
            dataType: 'json',
            success: function (response) {
                alert(response.message || "Xóa người dùng thành công!");
                fetchUsers();
            },
            error: function (xhr) {
                console.error('Lỗi khi xóa người dùng:', xhr.responseText);
                alert(xhr.responseJSON?.message || "Không thể xóa người dùng. Vui lòng thử lại sau.");
            }
        });
    }
}

//---------------Edit user data--------------------//
function openEditPopup(event) {
    const userName = event.target.getAttribute("data-username");

    const main = event.target.closest("main");
    const overlay = main.querySelector(".overlay");
    overlay.style.display = "block";

    $.ajax({
        url: `/WebBanQuanAo/admin/api/users/${encodeURIComponent(userName)}`,
        type: 'GET',
        dataType: 'json',
        success: function (response) {
            const data = response.data
            document.getElementById("edit-id").value = data.id;
            document.getElementById("edit-username").value = data.userName;
            document.getElementById("edit-lastName").value = data.lastName;
            document.getElementById("edit-firstName").value = data.firstName;
            document.getElementById("edit-email").value = data.email;
            document.getElementById("edit-address").value = data.address || '';
            document.getElementById("edit-phone").value = data.phone || '';
            document.getElementById("edit-avatar").value = data.avatar || '';
            document.getElementById("edit-createdDate").value = data.createdAt;

            const selectStatus = document.getElementById("edit-status");
            const optionStatus = selectStatus.querySelector(`option[value="${data.status}"]`);
            if(optionStatus) optionStatus.selected = true;
        },
        error: function (xhr, status, error) {
            console.error("Lỗi khi lấy dữ liệu người dùng", error);
            const message = xhr.responseJSON?.message || "Không thể lấy thông tin người dùng. Vui lòng thử lại.";
            alert(message)
        }
    });
}

function saveUserEdits(event) {
    event.preventDefault();

    const user = {
        id: parseInt(document.getElementById("edit-id").value),
        userName: document.getElementById("edit-username").value,
        firstName: document.getElementById("edit-firstName").value,
        lastName: document.getElementById("edit-lastName").value,
        email: document.getElementById("edit-email").value,
        avatar: document.getElementById("edit-avatar").value,
        address: document.getElementById("edit-address").value,
        phone: parseInt(document.getElementById("edit-phone").value) || null,
        status: parseInt(document.getElementById("edit-status").value),
        createdAt: new Date(document.getElementById("edit-createdDate").value).toISOString(),
    };

    console.log("JSON object gửi đi:", JSON.stringify(user));

    $.ajax({
        url: `/WebBanQuanAo/admin/api/users/${encodeURIComponent(user.userName)}`,
        type: 'PUT',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(user),
        success: function (response) {
            alert(response.message || "Cập nhật thông tin người dùng thành công!");
            fetchUsers();
            hideOverlay();  // giữ nguyên hàm này nếu bạn đã có
        },
        error: function (xhr, status, error) {
            console.error("Lỗi khi cập nhật thông tin người dùng:", error);
            console.error("Chi tiết lỗi:", xhr.responseText);
            alert(xhr.responseJSON?.message || "Không thể cập nhật người dùng. Vui lòng thử lại.");
        }
    });
}

/*--------------------------------------------------------
                  Add Users
----------------------------------------------------------*/

// Hiển thị form và lớp phủ
function showAddUserForm() {
    const overlay = document.querySelector(".overlay-addUser");
    overlay.style.display = "flex";
}

// Ẩn form và lớp phủ
function hideAddUserForm() {
    const overlay = document.querySelector(".overlay-addUser");
    const form = document.getElementById('add-user-form');
    form.reset();
    overlay.style.display = "none";
}

function createUser(event) {
    event.preventDefault();

    const form = document.getElementById('add-user-form');
    const userData = Object.fromEntries(new FormData(form).entries());

    fetch('/WebBanQuanAo/admin/api/users', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(userData)
    })
        .then(response => response.ok ? response.json() : response.json().then(err => Promise.reject(err)))
        .then(data => {
            alert(data.message || 'Người dùng đã được tạo thành công!');
            fetchUsers();
            hideAddUserForm();
        })
        .catch(err => {
            console.error('Error:', err);
            alert(err.message || 'Không thể tạo người dùng. Vui lòng thử lại.');
        });
}

window.addEventListener("DOMContentLoaded", hideAddUserForm);

document.addEventListener("click", function(event) {
    const overlay = document.querySelector(".overlay-addUser");
    const form = document.getElementById("add-user-form");
    if (overlay.style.display === "flex"
        && !form.contains(event.target)
        && !event.target.closest("button")) {
        hideAddUserForm();
    }
});