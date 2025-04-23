/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
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

