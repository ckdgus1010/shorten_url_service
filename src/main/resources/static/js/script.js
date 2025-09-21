const submitBtn = document.querySelector("#submit-btn");
submitBtn.addEventListener("click", submit);

const copyBtn = document.querySelector("#copy-btn");
copyBtn.addEventListener("click", copy);

async function submit(event) {
    event.preventDefault();

    const form = document.getElementById("url-form");
    const formData = new FormData(form);
    const originalUrl = formData.get("original-url").trim();

    if (originalUrl) {
        await shortenUrl(originalUrl);
    } else {
        alert("단축할 URL을 입력해 주세요.");
    }
}

async function shortenUrl(url) {

    try {
        const response = await fetch("/api/urls", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                "originalUrl": url,
            }),
        });

        const status = response.status;
        const data = await response.json();

        if (status === 200) {
            showResultBox(data.result);
        } else {
            alert(status + " ::: " + data.message);
        }
    } catch (err) {
        alert(err);
    }
}

function showResultBox(url) {
    const resultBox = document.querySelector("#result-box");
    resultBox.classList.remove("hidden");

    const input = document.querySelector("#shortened-url");
    input.value = url;
}

function copy() {
    const url = document.querySelector("#shortened-url").value;

    navigator.clipboard
        .writeText(url)
        .then(() => {
            alert("복사되었습니다.");
        })
        .catch((err) => {
            alert("복사를 할 수 없습니다.");
        });
}
