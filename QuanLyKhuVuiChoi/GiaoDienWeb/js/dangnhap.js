function showTab(tabId) {

    const forms = document.querySelectorAll('.tab-content');
    forms.forEach(form => form.classList.add('hidden'));


    const activeForm = document.getElementById(tabId);
    if (activeForm) activeForm.classList.remove('hidden');


    const buttons = document.querySelectorAll('.tab-btn');
    buttons.forEach(btn => btn.classList.remove('active'));


    const activeBtn = Array.from(buttons).find(btn =>
        btn.textContent.includes(tabId === 'login' ? 'Đăng nhập' : 'Đăng ký')
    );

    if (activeBtn) {
        activeBtn.classList.add('active');
    }
}
