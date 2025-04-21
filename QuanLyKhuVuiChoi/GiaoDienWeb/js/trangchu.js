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