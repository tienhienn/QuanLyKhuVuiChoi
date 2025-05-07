// Hiển thị tab đăng nhập/đăng ký
function showTab(tabId) {
    // 1. Ẩn tất cả form (giả sử các form có class 'tab-content')
    const forms = document.querySelectorAll('.tab-content');
    forms.forEach(form => form.classList.add('hidden'));

    // 2. Hiện form tương ứng
    const activeForm = document.getElementById(tabId);
    if (activeForm) activeForm.classList.remove('hidden');

    // 3. Cập nhật trạng thái active cho các nút tab (class 'tab-btn')
    const buttons = document.querySelectorAll('.tab-btn');
    buttons.forEach(btn => btn.classList.remove('active'));

    // 4. Tìm nút phù hợp để active (dùng text hoặc thứ tự)
    const activeBtn = Array.from(buttons).find(btn =>
        btn.textContent.includes(tabId === 'login' ? 'Đăng nhập' : 'Đăng ký')
    );

    if (activeBtn) {
        activeBtn.classList.add('active');
    }
}

// Kiểm tra trạng thái đăng nhập khi tải trang
document.addEventListener("DOMContentLoaded", function() {
    // Kiểm tra đã đăng nhập chưa
    checkLoginStatus();
    
    // Đăng ký các event listener
    setupLoginForm();
    setupRegisterForm();
});

// Kiểm tra người dùng đã đăng nhập chưa
function checkLoginStatus() {
    fetch("http://localhost:8080/api/auth/profile", {
        method: "GET",
        credentials: "include"  // Quan trọng để gửi cookie session
    })
    .then(response => {
        if (response.ok) {
            // Người dùng đã đăng nhập, chuyển đến trang đã đăng nhập
            window.location.href = "/src/main/resources/static/views/html/trangchuDaDangnhap.html";
            return;
        }
        // Nếu không đăng nhập, hiển thị form đăng nhập
        showTab('login');
    })
    .catch(error => {
        console.error('Error checking login status:', error);
        // Hiển thị form đăng nhập nếu có lỗi
        showTab('login');
    });
}

// Thiết lập form đăng nhập
function setupLoginForm() {
    const loginForm = document.getElementById("login");
    if (!loginForm) return;

    loginForm.addEventListener("submit", function(e) {
        e.preventDefault(); // Chặn reload trang

        const email = document.getElementById("loginEmail").value;
        const password = document.getElementById("loginPassword").value;

        const loginRequest = {
            email: email,
            matkhau: password
        };

        fetch("http://localhost:8080/api/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            credentials: "include", // Quan trọng để lưu cookie session
            body: JSON.stringify(loginRequest)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Đăng nhập thất bại');
            }
            return response.json();
        })
        .then(data => {
            console.log("Đăng nhập thành công:", data);
            
            // Lưu thông tin đăng nhập vào localStorage để duy trì trạng thái
            if (data && data.message) {
                localStorage.setItem('isLoggedIn', 'true');
                
                // Chuyển hướng đến trang đã đăng nhập
                window.location.href = "/src/main/resources/static/views/html/trangchu.html";
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert("Đăng nhập thất bại! Vui lòng kiểm tra email và mật khẩu.");
        });
    });
}

// Thiết lập form đăng ký
function setupRegisterForm() {
    const registerForm = document.getElementById("register");
    if (!registerForm) return;

    registerForm.addEventListener("submit", function(e) {
        e.preventDefault();

        const fullName = document.getElementById("fullName").value;
        const email = document.getElementById("email").value;
        const phone = document.getElementById("phone").value;
        const password = document.getElementById("password").value;
        const confirmPassword = document.getElementById("confirmPassword").value;
        
        const emailRegex = /^[A-Za-z][A-Za-z0-9._%+-]*@gmail\.com$/;
        if (!emailRegex.test(email)) {
            alert("Email phải bắt đầu bằng chữ cái và có định dạng @gmail.com!");
            return;
        }

        if (password !== confirmPassword) {
            alert("Mật khẩu không khớp!");
            return;
        }

        const requestBody = {
            tenKhachHang: fullName,
            matkhau: password,
            sdt: phone,
            email: email
        };
        
        // Gửi yêu cầu đăng ký tới backend
        fetch("http://localhost:8080/api/auth/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(requestBody)
        })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => {
                    throw new Error(text || 'Đăng ký thất bại');
                });
            }
            return response.json();
        })
        .then(data => {
            alert("Đăng ký thành công!");
            showTab('login');
        })
        .catch(error => {
            console.error("Lỗi khi gọi API:", error);
            alert("Đăng ký thất bại! " + error.message);
        });
    });
}

// Hàm đăng xuất
function logout() {
    fetch("http://localhost:8080/api/auth/logout", {
        method: "POST",
        credentials: "include"
    })
    .then(() => {
        // Xóa thông tin đăng nhập khỏi localStorage
        localStorage.removeItem('isLoggedIn');
        // Chuyển về trang đăng nhập
        window.location.href = "/src/main/resources/static/views/html/login.html";
    })
    .catch(error => {
        console.error('Lỗi khi đăng xuất:', error);
        // Vẫn chuyển về trang đăng nhập nếu có lỗi
        localStorage.removeItem('isLoggedIn');
        window.location.href = "/src/main/resources/static/views/html/login.html";
    });
}