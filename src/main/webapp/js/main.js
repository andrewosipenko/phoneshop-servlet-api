function showPopup(index) {
    let element = document.getElementById("popup" + index);
    if (element.style.display === "none") {
        element.style.display = "block"
        element.style.visibility = "visible"
    } else {
        element.style.display = "none"
    }
}
