document
    .querySelector("#submit-btn")
    .addEventListener('click', submit);

function submit(event) {
    event.preventDefault();
    console.log("submit");

    showResultBox();
}

function showResultBox() {
    document.querySelector("#result-box").classList.remove("hidden");
}