document.addEventListener('DOMContentLoaded', () => {
    fetch('http://localhost:8080/api/tours') // Đảm bảo URL backend chính xác
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(tours => {
            const tourListContainer = document.getElementById('tour-list-container');
            if (tourListContainer) {
                let html = '';
                tours.forEach(tour => {
                    const formattedPrice = tour.giaTour ? tour.giaTour.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' }) : 'Liên hệ';
                    const formattedStartDate = tour.tg_batDau ? new Date(tour.tg_batDau).toLocaleDateString('vi-VN') : '';
                    const formattedEndDate = tour.tg_ketThuc ? new Date(tour.tg_ketThuc).toLocaleDateString('vi-VN') : '';

                    html += `
                        <div class="tour-card">
                            <h2>${tour.tenTour || ''}</h2>
                            <ul class="tour-info">
                                ${tour.moTa ? `<li>${tour.moTa}</li>` : ''}
                                <li><strong>Giá tour:</strong> ${formattedPrice}</li>
                                ${formattedStartDate ? `<li><strong>Thời gian bắt đầu:</strong> ${formattedStartDate}</li>` : ''}
                                ${formattedEndDate ? `<li><strong>Thời gian kết thúc:</strong> ${formattedEndDate}</li>` : ''}
                                ${tour.soLuongMax ? `<li><strong>Số lượng tối đa:</strong> ${tour.soLuongMax} người</li>` : ''}
                                ${tour.soLuongConLai ? `<li><strong>vé còn lại:</strong> ${tour.soLuongConLai} người</li>` : ''}
                            </ul>
                            <div class="price-section">
                                <h3>CHI TIẾT GIÁ</h3>
                                <p style="font-size: 24px; font-weight: bold;">Giá: <span>${formattedPrice}</span></p>
                                <a href="/src/main/resources/static/views/html/booking.html?maTour=${tour.maTour || ''}">
                                    <button class="btn-booking"><i>🛒</i> Chọn đặt tour này</button>
                                </a>
                            </div>
                        </div>
                    `;
                });
                tourListContainer.innerHTML = html;
            }
        })
        .catch(error => {
            console.error('Lỗi khi lấy danh sách tour:', error);
            const tourListContainer = document.getElementById('tour-list-container');
            if (tourListContainer) {
                tourListContainer.innerHTML = '<p class="error-message">Không thể tải danh sách tour.</p>';
            }
        });
});
//hiển thị tên user
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