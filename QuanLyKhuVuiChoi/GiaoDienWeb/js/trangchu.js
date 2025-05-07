document.addEventListener("DOMContentLoaded", function () {
    flatpickr("#tg_date_choose", {
        dateFormat: "d/m/Y",
        minDate: "today"
    });
});



document.addEventListener("DOMContentLoaded", function () {
    const updateButtonState = (input) => {
        const value = parseInt(input.value) || 0;
        const minusBtn = input.parentElement.querySelector('.btn_updown_minus');
        const inputName = input.getAttribute('name');

        let min = 0;
        if (inputName === "adultQuantity") min = 2;

        if (value <= min) {
            minusBtn.classList.add('disabled');
            minusBtn.style.opacity = '0.5';
            minusBtn.style.pointerEvents = 'none';
        } else {
            minusBtn.classList.remove('disabled');
            minusBtn.style.opacity = '1';
            minusBtn.style.pointerEvents = 'auto';
        }
    };

    const buttons = document.querySelectorAll('.button_change_quantity');
    buttons.forEach(button => {
        button.addEventListener('click', function () {
            const input = this.parentElement.querySelector('.quantity_people');
            const inputName = input.getAttribute('name');
            let value = parseInt(input.value) || 0;

            let min = 0;
            if (inputName === "adultQuantity") min = 2;

            if (this.classList.contains('btn_updown_plus')) {
                input.value = value + 1;
            } else if (this.classList.contains('btn_updown_minus')) {
                if (value > min) {
                    input.value = value - 1;
                }
            }
            updateButtonState(input);
        });

        // Cập nhật trạng thái ban đầu
        const input = button.parentElement.querySelector('.quantity_people');
        updateButtonState(input);
    });
});

document.addEventListener("DOMContentLoaded", function () {
    const button = document.querySelector('.tg_hv_1');  // Nút "Đặt vé"
    
    button.addEventListener('click', function(event) {
        event.preventDefault(); // Chặn hành động mặc định (chuyển hướng)

        // Gọi API để kiểm tra xem người dùng đã đăng nhập chưa
        fetch('http://localhost:8080/api/auth/profile', {
            method: 'GET',
            credentials: 'include' // Đảm bảo gửi cookie/session
        })
        .then(response => {
            if (!response.ok) {
                // Nếu chưa đăng nhập, chuyển hướng đến trang đăng nhập
                alert("Vui Lòng Đăng Nhập Trước Khi Sử Dụng Dịch Vụ!!!");
                window.location.href = "/src/main/resources/static/views/html/dangnhap.html";
            } else {
                // Nếu đã đăng nhập, tiến hành chuyển đến trang "Đặt vé"
                const dateValue = document.getElementById("tg_date_choose").value;
                localStorage.setItem("ngayDi", dateValue);
                console.log("Ngày đi được chọn là:", dateValue);
                window.location.href = "/src/main/resources/static/views/html/datve.html"; // Điều hướng tới trang datve.html
            }
        })
        .catch(error => {
            console.error('Lỗi khi kiểm tra thông tin người dùng:', error);
            // Nếu có lỗi xảy ra, vẫn chuyển hướng đến đăng nhập
            window.location.href = "/src/main/resources/static/views/html/dangnhap.html";
        });
    });
});


document.addEventListener('DOMContentLoaded', () => {
    // Gọi API để lấy tên khách hàng từ session
    fetch('http://localhost:8080/api/auth/profile', {
        method: 'GET',
        credentials: 'include' // Đảm bảo gửi cookie/session
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        const userName = data.message.replace('Tên khách hàng: ', '').split(',')[0]; // Chỉ lấy tên

        const userNameElement = document.getElementById('user-name');
        if (userNameElement) {
            userNameElement.innerText = `${userName}`;
        }

        // Ẩn thanh đăng nhập và hiện user-info
        const headerRightSection = document.getElementById('header__right');
        const userInfoSection = document.getElementById('user-info');

        // Ẩn thanh đăng nhập
        if (headerRightSection) headerRightSection.style.display = 'none';

        // Hiển thị thông tin người dùng
        if (userInfoSection) userInfoSection.style.display = 'flex'; // hoặc 'block' tùy vào layout của bạn
    })
    .catch(error => {
        console.error('Lỗi khi lấy thông tin người dùng:', error);
    });
});
document.addEventListener("DOMContentLoaded", function () {
    const datveLink = document.getElementById('datve-link');  // Nút "Đặt vé"
    
    datveLink.addEventListener('click', function(event) {
        event.preventDefault(); // Chặn hành động mặc định (chuyển hướng)

        // Gọi API để kiểm tra xem người dùng đã đăng nhập chưa
        fetch('http://localhost:8080/api/auth/profile', {
            method: 'GET',
            credentials: 'include' // Đảm bảo gửi cookie/session
        })
        .then(response => {
            if (!response.ok) {
                // Nếu chưa đăng nhập, chuyển hướng đến trang đăng nhập
                window.location.href = "/src/main/resources/static/views/html/dangnhap.html";
                alert("Vui Lòng Đăng Nhập Trước Khi Sử Dụng Dịch Vụ!!!");
            } else {
                // Nếu đã đăng nhập, tiến hành chuyển đến trang "Đặt vé"
                const dateValue = document.getElementById("tg_date_choose").value;
                localStorage.setItem("ngayDi", dateValue);
                console.log("Ngày đi được chọn là:", dateValue);
                window.location.href = "/src/main/resources/static/views/html/datve.html"; // Điều hướng tới trang datve.html
            }
        })
        .catch(error => {
            console.error('Lỗi khi kiểm tra thông tin người dùng:', error);
            // Nếu có lỗi xảy ra, vẫn chuyển hướng đến đăng nhập
            window.location.href = "/src/main/resources/static/views/html/dangnhap.html";
        });
    });
});








