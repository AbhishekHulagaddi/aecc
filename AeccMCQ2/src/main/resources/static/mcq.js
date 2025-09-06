let questions = [];
let currentQuestionIndex = 0;
let userAnswers = {};
let timerDuration = 30 * 60; // 30 minutes in seconds (1800)
let isSubmitted = false; // ✅ Flag to avoid multiple submissions

document.addEventListener("DOMContentLoaded", () => {
    const storedData = JSON.parse(sessionStorage.getItem("mcqData"));
    if (!storedData) {
        alert("No test data found!");
        window.location.href = "../userDashboard.html";
        return;
    }


    questions = storedData.questions.Questions;

    renderQuestion();

    document.getElementById("prevBtn").addEventListener("click", showPrev);
    document.getElementById("nextBtn").addEventListener("click", showNext);
    document.getElementById("submitBtn").addEventListener("click", submitTest);

    startTimer();
});

function renderQuestion() {
    const q = questions[currentQuestionIndex];
    const options = [q.optionA, q.optionB, q.optionC, q.optionD];

    const container = document.getElementById("question-container");
    container.innerHTML = `
        <div class="question">
            <h3>Q${currentQuestionIndex + 1}. ${q.questionName}</h3>
            <div class="options">
                ${options.map((opt, idx) => `
                    <label class="option">
                        <input type="radio" name="answer" value="${idx}"
                        ${userAnswers[currentQuestionIndex] == idx ? "checked" : ""}>
                        <span>${opt}</span>
                    </label>
                `).join('')}
            </div>
        </div>
    `;

document.querySelectorAll("input[name='answer']").forEach(input => {
    input.addEventListener("change", (e) => {
        userAnswers[currentQuestionIndex] = parseInt(e.target.value);

        // Remove "selected" class from all options first
        document.querySelectorAll(".option").forEach(opt => opt.classList.remove("selected"));

        // Add "selected" class to chosen option
        e.target.closest(".option").classList.add("selected");
    });
});

// Restore selection state on re-render
if (userAnswers[currentQuestionIndex] !== undefined) {
    const selectedIndex = userAnswers[currentQuestionIndex];
    const selectedOption = document.querySelectorAll(".option")[selectedIndex];
    if (selectedOption) {
        selectedOption.classList.add("selected");
        selectedOption.querySelector("input").checked = true;
    }
}
}







function showPrev() {
    if (currentQuestionIndex > 0) {
        currentQuestionIndex--;
        renderQuestion();
    }
}

function showNext() {
    if (currentQuestionIndex < questions.length - 1) {
        currentQuestionIndex++;
        renderQuestion();
    }
}



function startTimer() {
    let timer = setInterval(() => {
        if (isSubmitted) { 
            clearInterval(timer); // Stop timer if already submitted
            return;
        }

        timerDuration--;
        const minutes = Math.floor(timerDuration / 60);
        const seconds = timerDuration % 60;
        document.getElementById("timer").textContent =
            `${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;

        if (timerDuration <= 0) {
            clearInterval(timer);
            submitTest(true); // ✅ auto-submit when time is up
        }
    }, 1000);
}

function showSpinner() {
    document.getElementById("spinner").style.display = "flex";
    const submitBtn = document.getElementById("submitBtn");
    if (submitBtn) {
        submitBtn.disabled = true; // ✅ Prevent multiple clicks
        submitBtn.textContent = "Submitting..."; // Optional
    }
}

function hideSpinner(success = false) {
    document.getElementById("spinner").style.display = "none";
    const submitBtn = document.getElementById("submitBtn");
    if (submitBtn && !success) {
        // ✅ Only re-enable if submission failed
        submitBtn.disabled = false;
        submitBtn.textContent = "Submit";
    }
}

async function submitTest() {

    if (isSubmitted) return; // ✅ Prevent multiple submissions
        isSubmitted = true;
     showSpinner(); // ✅ Show spinner before API call

    const userId = sessionStorage.getItem("userId"); // assuming you store logged-in userId

    // Prepare payload for API
    const resultModels = questions.map((q, index) => {
        const answeredIndex = userAnswers[index];
        const options = [q.optionA, q.optionB, q.optionC, q.optionD];
        return {
            resultId: null,
            answeredBy: userId,
            questionId: q.questionId,
            question: q.questionName,
            answered: answeredIndex !== undefined ? options[answeredIndex] : null,
            isCorrect: false,
            isSkipped: answeredIndex === undefined,
            answeredAt: new Date().toISOString(),
            optionA: q.optionA,
            optionB: q.optionB,
            optionC: q.optionC,
            optionD: q.optionD,
            correctAnswer: null
        };
    });

    console.log("Submitting Results:", resultModels);

    try {
  //      const response = await fetch("http://localhost:8081/Result/Result/Create", {
        const response = await fetch("http://tierraagniveshacoaching.up.railway.app/Result/Result/Create", {     
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + sessionStorage.getItem("token") // if JWT auth
            },
            body: JSON.stringify(resultModels)
        });

        if (!response.ok) {
            throw new Error("Failed to submit test. Status: " + response.status);
        }

        const result = await response.json();
        console.log("Server Response:", result);
        hideSpinner();

        // Hide test UI
        document.getElementById("question-container").style.display = "none";
        document.getElementById("prevBtn").style.display = "none";
        document.getElementById("nextBtn").style.display = "none";
        document.getElementById("submitBtn").style.display = "none";

        // Show styled result card
        const scoreContainer = document.getElementById("score-container");
        scoreContainer.innerHTML = `
            <div class="result-card">
                    <div class="result-actions top-exit">
                         <button id="exit-btn-top">🚪 Exit</button>
                    </div>
                <h2>🎉 Test Completed</h2>
                <div class="result-details">
                    <p><strong>Subject:</strong> ${result.subjectName}</p>
                    <p><strong>Section:</strong> ${result.section}</p>
                    <p><strong>Score:</strong> ${result.score} / 30</p>
                    <p><strong>Percentage:</strong> ${result.percentage}</p>
                </div>

                <div class="result-stats">
                    <div class="stat correct">✅ Correct: <span>${result.correctAnswers}</span></div>
                    <div class="stat wrong">❌ Wrong: <span>${result.wrongAnswers}</span></div>
                    <div class="stat skipped">⏭ Skipped: <span>${result.skippedAnswers}</span></div>
                </div>

                

                <h3>📊 Answer Review:</h3>
                <div class="review">
                    ${result.resultModels.map((r, i) => `
                        <div class="review-question ${r.correct ? "correct" : "wrong"}">
                            <p><b>Q${i+1}.</b> ${r.question}</p>
                            <p><b>Your Answer:</b> ${r.answered ? r.answered : "❌ Skipped"}</p>
                            <p><b>Correct Answer:</b> ${r.correctAnswer}</p>
                        </div>
                    `).join("")}
                </div>

                <div class="result-actions">
                    <button id="exit-btn">🚪 Exit</button>
                </div>
            </div>
        `;
        scoreContainer.style.display = "block";

        // Add Exit button action
        document.getElementById("exit-btn").addEventListener("click", function () {
            window.location.href = "../userDashboard.html";
        });

        document.getElementById("exit-btn-top").addEventListener("click", () => {
            window.location.href = "../userDashboard.html";
        });

    } catch (error) {
        console.error("Error submitting test:", error);
        alert("There was a problem submitting your test. Please try again.");
        hideSpinner();
    }
}

