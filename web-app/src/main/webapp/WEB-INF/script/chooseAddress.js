
const selectAddress = document.getElementById("address-select");

selectAddress.addEventListener("change", function () {

  const selectedOption = selectAddress.options[selectAddress.selectedIndex];
  const region = selectedOption.getAttribute("data-region");
  const town = selectedOption.getAttribute("data-town");
  const exactAddress = selectedOption.getAttribute("data-exactAddress");
  const postalCode = selectedOption.getAttribute("data-postalCode");

  document.getElementById("region").value = region;
  document.getElementById("town").value = town;
  document.getElementById("exactAddress").value = exactAddress;
  document.getElementById("postalCode").value = postalCode;
  document.getElementById("addressId").value = selectAddress.value;
});