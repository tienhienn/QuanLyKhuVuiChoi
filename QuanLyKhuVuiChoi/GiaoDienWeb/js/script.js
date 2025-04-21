document.addEventListener("DOMContentLoaded", () => {
    const checkboxes = document.querySelectorAll(".ticket-checkbox");
    const selectedItems = document.getElementById("selected-items");
    const totalPriceDisplay = document.getElementById("total-price");
  
    function formatCurrency(number) {
      return number.toLocaleString("vi-VN") + " vnđ";
    }
  
    function updateTotal() {
      selectedItems.innerHTML = "";
      let total = 0;
  
      checkboxes.forEach((cb) => {
        if (cb.checked) {
          const name = cb.dataset.name;
          const price = parseInt(cb.dataset.price);
          total += price;
          const p = document.createElement("p");
          p.textContent = `${name} - ${formatCurrency(price)}`;
          selectedItems.appendChild(p);
        }
      });
  
      totalPriceDisplay.textContent = formatCurrency(total);
    }
  
    checkboxes.forEach((cb) => cb.addEventListener("change", updateTotal));
    updateTotal(); // Khởi động khi tải trang
  });
  