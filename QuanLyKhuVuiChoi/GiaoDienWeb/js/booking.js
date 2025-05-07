document.addEventListener("DOMContentLoaded", function () {
  const ticketContainers = document.querySelectorAll(".ticket-container");
  const paymentBox = document.querySelector(".payment-box");
  const totalLine = document.getElementById("total-line");
  const urlParams = new URLSearchParams(window.location.search);
  const maTour = urlParams.get("maTour");

  function createSummaryLine(id) {
    const div = document.createElement("div");
    div.className = "summary-line";
    div.id = id;
    div.style.display = "none";
    return div;
  }

  let ticketSummaryMap = new Map();

  ticketContainers.forEach((container, index) => {
    const checkbox = container.querySelector(".ant-checkbox-input");
    const ticketTitleElement = container.querySelector(".ticket-title");
    const adultGroup = container.querySelector(".ticket-group");
    const childGroup = container.querySelector("#child-ticket .ticket-group");
    const seniorGroup = container.querySelector("#senior-ticket .ticket-group");
    const btnAddChild = container.querySelector("#add-child");
    const btnAddSenior = container.querySelector("#add-senior");
    const childTicket = container.querySelector("#child-ticket");
    const seniorTicket = container.querySelector("#senior-ticket");
    const tourNameElement = container.querySelector(".tour-name"); // Thay đổi selector ở đây

    // Tạo các dòng tóm tắt
    const titleId = `ticket-title-${index}`;
    const lineAdultId = `line-adult-${index}`;
    const lineChildId = `line-child-${index}`;
    const lineSeniorId = `line-senior-${index}`;

    const titleDiv = document.createElement("div");
    titleDiv.id = titleId;
    titleDiv.style.marginBottom = "5px";

    const lineAdult = createSummaryLine(lineAdultId);
    const lineChild = createSummaryLine(lineChildId);
    const lineSenior = createSummaryLine(lineSeniorId);

    paymentBox.insertBefore(titleDiv, totalLine);
    paymentBox.insertBefore(lineAdult, totalLine);
    paymentBox.insertBefore(lineChild, totalLine);
    paymentBox.insertBefore(lineSenior, totalLine);

    ticketSummaryMap.set(container, {
      titleDiv,
      lineAdult,
      lineChild,
      lineSenior,
      checkbox,
      adultGroup,
      childGroup,
      seniorGroup,
      childTicket,
      seniorTicket,
      ticketTitleElement,
      tourNameElement, // Lưu lại phần tử tên tour
    });

    btnAddChild?.addEventListener("click", () => {
      childTicket.style.display = "block";
      btnAddChild.style.display = "none";
      updateSummary();
    });

    btnAddSenior?.addEventListener("click", () => {
      seniorTicket.style.display = "block";
      btnAddSenior.style.display = "none";
      updateSummary();
    });

    container.querySelectorAll(".counter").forEach((counter) => {
      const minusBtn = counter.querySelector("button:first-of-type");
      const plusBtn = counter.querySelector("button:last-of-type");
      const countSpan = counter.querySelector("span:nth-of-type(2)");

      minusBtn.addEventListener("click", () => {
        let count = parseInt(countSpan.textContent);
        const data = ticketSummaryMap.get(container);

        const isAdult = counter.closest(".ticket-group") === data.adultGroup;
        const isChild = counter.closest(".ticket-group") === data.childGroup;
        const isSenior = counter.closest(".ticket-group") === data.seniorGroup;

        if (isAdult && count <= 2) return;

        if (count > 0) {
          count--;
          countSpan.textContent = count.toString();

          if (isChild && count === 0) {
            data.childTicket.style.display = "none";
            const addChildBtn = container.querySelector("#add-child");
            if (addChildBtn) addChildBtn.style.display = "inline-block";
          }

          if (isSenior && count === 0) {
            data.seniorTicket.style.display = "none";
            const addSeniorBtn = container.querySelector("#add-senior");
            if (addSeniorBtn) addSeniorBtn.style.display = "inline-block";
          }

          updateSummary();
        }
      });

      plusBtn.addEventListener("click", () => {
        let count = parseInt(countSpan.textContent);
        count++;
        countSpan.textContent = count.toString();
        updateSummary();
      });
    });

    checkbox.addEventListener("change", updateSummary);
  });

  function getCountAndPrice(group) {
    if (!group) return [0, 0];
    const priceText = group.querySelector(".price").textContent;
    const countText = group.querySelector("span:nth-of-type(2)").textContent;
    const price = parseInt(priceText.replace(/[^\d]/g, ""));
    const count = parseInt(countText);
    return [count, price];
  }

  function updateSummary() {
    let grandTotal = 0;

    ticketSummaryMap.forEach((data, container) => {
      const {
        titleDiv,
        lineAdult,
        lineChild,
        lineSenior,
        checkbox,
        adultGroup,
        childGroup,
        seniorGroup,
        childTicket,
        seniorTicket,
        ticketTitleElement,
        tourNameElement, // Sử dụng tourNameElement
      } = data;

      if (!checkbox.checked) {
        titleDiv.style.display = "none";
        lineAdult.style.display = "none";
        lineChild.style.display = "none";
        lineSenior.style.display = "none";
        return;
      }

      const ticketTitle = container.querySelector(".ticket-title").innerText.trim();
      titleDiv.style.display = "block";
      titleDiv.innerHTML = `<strong>${ticketTitle}</strong>`;

      const [adultCount, adultPrice] = getCountAndPrice(adultGroup);
      const [childCount, childPrice] = getCountAndPrice(childGroup);
      const [seniorCount, seniorPrice] = getCountAndPrice(seniorGroup);

      let subtotal = 0;

      if (adultCount > 0) {
        lineAdult.style.display = "flex";
        lineAdult.innerHTML = `<span>Người lớn x${adultCount}</span><span>${(
          adultCount * adultPrice
        ).toLocaleString("vi-VN")} vnđ</span>`;
        subtotal += adultCount * adultPrice;
      } else {
        lineAdult.style.display = "none";
      }

      if (childTicket.style.display !== "none" && childCount > 0) {
        lineChild.style.display = "flex";
        lineChild.innerHTML = `
          <span>Trẻ em x${childCount}</span>
          <span>${(childCount * childPrice).toLocaleString("vi-VN")} vnđ</span>
        `;
        subtotal += childCount * childPrice;
      } else {
        lineChild.style.display = "none";
      }

      if (seniorTicket.style.display !== "none" && seniorCount > 0) {
        lineSenior.style.display = "flex";
        lineSenior.innerHTML = `
          <span>Người cao tuổi x${seniorCount}</span>
          <span>${(seniorCount * seniorPrice).toLocaleString("vi-VN")} vnđ</span>
        `;
        subtotal += seniorCount * seniorPrice;
      } else {
        lineSenior.style.display = "none";
      }

      grandTotal += subtotal;
    });

    totalLine.lastElementChild.textContent =
      grandTotal.toLocaleString("vi-VN") + " vnđ";
  }

  updateSummary();

  if (maTour) {
    fetch(`http://localhost:8080/api/tours/${maTour}`)
      .then((response) => {
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
      })
      .then((tourDetail) => {
        const ticketTitleElement = document.querySelector(".ticket-container .ticket-title");
        const priceAdultElement = document.querySelector(".ticket-container .ticket-group:first-child .price");
        const tourNameElement = document.querySelector(".ticket-content > div"); // Selector thay đổi ở đây
        const tourDateElement = document.querySelector("#tour-date");

        if (ticketTitleElement) {
          ticketTitleElement.textContent = tourDetail.tenTour || "Không có thông tin tour";
        }
        if (tourDateElement && tourDetail.ngayBatDau) {
          console.log("tourDetail:", tourDetail);
          console.log("tourDetail.ngayBatDau:", tourDetail ? tourDetail.ngayBatDau : "Không có dữ liệu");
          const date = new Date(tourDetail.ngayBatDau);  // Tạo đối tượng Date từ chuỗi 'YYYY-MM-DD'
          
          // Kiểm tra xem đối tượng Date có hợp lệ không
          if (isNaN(date)) {
            console.log("Ngày không hợp lệ");
          } else {
            const options = { day: '2-digit', month: '2-digit', year: 'numeric' };  // Định dạng ngày tháng năm
            const formattedDate = date.toLocaleDateString('vi-VN', options);  // Định dạng theo kiểu Việt Nam
            tourDateElement.textContent = formattedDate;
          }
        }
        
        if (priceAdultElement) {
          priceAdultElement.textContent = tourDetail.giaTour
            ? tourDetail.giaTour.toLocaleString("vi-VN", {
                style: "currency",
                currency: "VND",
              })
            : "Liên hệ";
        }

        if (tourNameElement) {
          tourNameElement.textContent = `Tên tour: ${tourDetail.tenTour}`; // Hiển thị tên tour
        }

        // Calculate and display prices
        const adultPrice = tourDetail.giaTour || 0;
        const childPrice = adultPrice * 0.7;
        const seniorPrice = adultPrice * 0.5;

        // Update the HTML with the calculated prices.  Use querySelectorAll to be more specific.
        const priceAdultElements = document.querySelectorAll(".ticket-container .ticket-group:first-child .price");
        priceAdultElements.forEach(el => el.textContent = adultPrice.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' }));

        const priceChildElements = document.querySelectorAll(".ticket-container #child-ticket .ticket-group .price");
        priceChildElements.forEach(el => el.textContent = childPrice.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' }));

        const priceSeniorElements = document.querySelectorAll(".ticket-container #senior-ticket .ticket-group .price");
        priceSeniorElements.forEach(el => el.textContent = seniorPrice.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' }));
      })
      .catch((error) => {
        console.error("Lỗi khi lấy chi tiết tour:", error);
        const ticketTitleElement = document.querySelector(".ticket-container .ticket-title");
        if (ticketTitleElement) {
          ticketTitleElement.textContent = "Không thể tải thông tin tour.";
        }
      });
  }
});

document.addEventListener("DOMContentLoaded", function () {
  const btnContinue = document.querySelector(".btn-pay");
  const step2 = document.getElementById("step-2");
  const ticketContainers = document.querySelectorAll(".ticket-container");

  btnContinue.addEventListener("click", function () {
    ticketContainers.forEach(function (ticket) {
      ticket.style.display = "none";
    });

    step2.style.display = "block";

    btnContinue.style.display = "none";
  });
});

document.addEventListener("DOMContentLoaded", () => {
  const btnContinue = document.querySelector(".btn-pay");
  const steps = document.querySelectorAll(".step-progress .step");

  btnContinue.addEventListener("click", () => {
    steps[1].classList.remove("current");
    steps[1].classList.add("done");
    steps[2].classList.remove("upcoming");
    steps[2].classList.add("current");
  });
});

document.addEventListener("DOMContentLoaded", function () {
  const btnContinue = document.querySelector(".btn-pay");
  const stepLabels = document.querySelectorAll(".step .label");
  const ticketContainers = document.querySelectorAll(".ticket-container");
  const step2 = document.getElementById("step-2");
  const steps = document.querySelectorAll(".step-progress .step");

  btnContinue.addEventListener("click", function () {
    ticketContainers.forEach((ticket) => (ticket.style.display = "none"));
    step2.style.display = "block";
    btnContinue.style.display = "none";

    steps[1].classList.remove("current");
    steps[1].classList.add("done");
    steps[2].classList.remove("upcoming");
    steps[2].classList.add("current");
  });

  stepLabels[1].addEventListener("click", function () {
    ticketContainers.forEach((ticket) => (ticket.style.display = "block"));
    step2.style.display = "none";
    btnContinue.style.display = "inline-block";

    steps[1].classList.remove("done");
    steps[1].classList.add("current");
    steps[2].classList.remove("current");
    steps[2].classList.add("upcoming");
  });
});

//đặt vé
document.addEventListener("DOMContentLoaded", function() {
  const ngayDi = localStorage.getItem("ngayDi");
    
  if (ngayDi) {
    // Hiển thị ngày đi lên giao diện
    document.getElementById("tour-date").textContent = ngayDi;
  } else {
    alert("Bạn chưa chọn ngày đi!");
    window.location.href = "/src/main/resources/static/views/html/trangchu.html"; // Quay lại trang chọn ngày
    return;  // Dừng lại nếu không có ngày đi
  }

  let ticketType = "Tour";  // Đảm bảo bạn sử dụng đúng biến

  // Lắng nghe sự kiện click vào nút thanh toán
  document.querySelector(".btn-payment").addEventListener("click", function () {
    const datVe = {
      ngayDi: ngayDi,
      loaiVe: ticketType  // Sử dụng ticketType thay vì loaiVe
    };

    // Gửi thông tin qua fetch API
    fetch('http://localhost:8080/api/datve', { 
      method: 'POST',
      credentials: 'include', 
      headers: {
        'Content-Type': 'application/json',
      },
      
      body: JSON.stringify(datVe),
    })
    .then(response => response.json())
    .then(data => {
      console.log('Dữ liệu đã được xử lý:', data);
      alert("Đăng ký thành công!");
      setTimeout(function() {
        window.location.href = "/src/main/resources/static/views/html/trangchuDaDangnhap.html";  // Thay bằng URL bạn muốn chuyển hướng
      }, 1000);
    })
    .catch((error) => {
      console.error('Có lỗi khi gửi dữ liệu:', error);
      alert("Đã có lỗi khi thanh toán, vui lòng thử lại.");
    });
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
