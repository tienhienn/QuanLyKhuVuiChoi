document.addEventListener("DOMContentLoaded", function () {
  const ticketContainers = document.querySelectorAll(".ticket-container");
  const paymentBox = document.querySelector(".payment-box");
  const totalLine = document.getElementById("total-line");

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
    const ticketTitle = container
      .querySelector(".ticket-title")
      .innerText.trim();

    const adultGroup = container.querySelector(".ticket-group");
    const childGroup = container.querySelector("#child-ticket .ticket-group");
    const seniorGroup = container.querySelector("#senior-ticket .ticket-group");

    const btnAddChild = container.querySelector("#add-child");
    const btnAddSenior = container.querySelector("#add-senior");

    const childTicket = container.querySelector("#child-ticket");
    const seniorTicket = container.querySelector("#senior-ticket");

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
    });

    // Gắn sự kiện thêm vé phụ
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

    // Tăng giảm số lượng
    container.querySelectorAll(".counter").forEach((counter) => {
      const minusBtn = counter.querySelector("button:first-of-type");
      const plusBtn = counter.querySelector("button:last-of-type");
      const countSpan = counter.querySelector("span:nth-of-type(2)");

      minusBtn.addEventListener("click", () => {
        let count = parseInt(countSpan.textContent);
        const data = ticketSummaryMap.get(container); // LẤY lại dữ liệu container tương ứng

        const isAdult = counter.closest(".ticket-group") === data.adultGroup;
        const isChild = counter.closest(".ticket-group") === data.childGroup;
        const isSenior = counter.closest(".ticket-group") === data.seniorGroup;

        if (isAdult && count <= 2) return; // giữ người lớn >= 2

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
      } = data;

      if (!checkbox.checked) {
        titleDiv.style.display = "none";
        lineAdult.style.display = "none";
        lineChild.style.display = "none";
        lineSenior.style.display = "none";
        return;
      }

      const ticketTitle = container
        .querySelector(".ticket-title")
        .innerText.trim();
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
        lineChild.innerHTML = `<span>Trẻ em x${childCount}</span><span>${(
          childCount * childPrice
        ).toLocaleString("vi-VN")} vnđ</span>`;
        subtotal += childCount * childPrice;
      } else {
        lineChild.style.display = "none";
      }

      if (seniorTicket.style.display !== "none" && seniorCount > 0) {
        lineSenior.style.display = "flex";
        lineSenior.innerHTML = `<span>Người cao tuổi x${seniorCount}</span><span>${(
          seniorCount * seniorPrice
        ).toLocaleString("vi-VN")} vnđ</span>`;
        subtotal += seniorCount * seniorPrice;
      } else {
        lineSenior.style.display = "none";
      }

      grandTotal += subtotal;
    });

    totalLine.lastElementChild.textContent =
      grandTotal.toLocaleString("vi-VN") + " vnđ";
  }

  updateSummary(); // chạy lần đầu
});

///
document.addEventListener("DOMContentLoaded", function () {
  const btnContinue = document.querySelector(".btn-pay");
  const step2 = document.getElementById("step-2");
  const ticketContainers = document.querySelectorAll(".ticket-container");

  btnContinue.addEventListener("click", function () {
    // Ẩn các vé khi nhấn "Tiếp tục"
    ticketContainers.forEach(function (ticket) {
      ticket.style.display = "none";
    });

    // Hiện Step 2 khi nhấn "Tiếp tục"
    step2.style.display = "block";

    // Ẩn nút "Tiếp tục" sau khi nhấn
    btnContinue.style.display = "none";
  });
});

document.addEventListener("DOMContentLoaded", () => {
  const btnContinue = document.querySelector(".btn-pay");
  const steps = document.querySelectorAll(".step-progress .step");

  btnContinue.addEventListener("click", () => {
    // Bước 2 từ current → done
    steps[1].classList.remove("current");
    steps[1].classList.add("done");
    // Bước 3 từ upcoming → current
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

  // Khi nhấn "Tiếp tục"
  btnContinue.addEventListener("click", function () {
    // Ẩn vé
    ticketContainers.forEach((ticket) => (ticket.style.display = "none"));
    // Hiện form thanh toán
    step2.style.display = "block";
    // Ẩn nút tiếp tục
    btnContinue.style.display = "none";

    // Cập nhật progress: bước 2 → done, bước 3 → current
    steps[1].classList.remove("current");
    steps[1].classList.add("done");
    steps[2].classList.remove("upcoming");
    steps[2].classList.add("current");
  });

  // Khi nhấn vào label "Dịch vụ mua thêm" (bước 2)
  stepLabels[1].addEventListener("click", function () {
    // Hiện lại vé
    ticketContainers.forEach((ticket) => (ticket.style.display = "block"));
    // Ẩn form thanh toán
    step2.style.display = "none";
    // Hiện lại nút "Tiếp tục"
    btnContinue.style.display = "inline-block";

    // Cập nhật progress: bước 2 → current, bước 3 → upcoming
    steps[1].classList.remove("done");
    steps[1].classList.add("current");
    steps[2].classList.remove("current");
    steps[2].classList.add("upcoming");
  });
});
