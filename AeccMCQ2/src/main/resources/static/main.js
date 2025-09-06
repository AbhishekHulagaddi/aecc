function showScreen(screenId) {
    document.querySelectorAll(".screen").forEach(screen => screen.classList.remove("active-screen"));
    document.getElementById(screenId).classList.add("active-screen");

    if (screenId === "result") {
        displayResutSections(); // 🔥 load results when Result screen is opened
    }
}

async function displayResutSections() {
    const container = document.getElementById("resultContainer");
    container.innerHTML = "<p>Loading...</p>";

    try {
   //     const response = await fetch("http://localhost:8081/Result/Result/FindSection", {
        const response = await fetch("https://tierraagniveshacoaching.up.railway.app/Result/Result/FindSection", {
 
            method: "POST",
            headers: {
                "Authorization": `Bearer ${userData.token}`,
                "Content-Type": "application/json"
            },
            body: JSON.stringify({})
        });

        if (!response.ok) throw new Error(`HTTP error! Status: ${response.status}`);

        const data = await response.json();
        container.innerHTML = "";

        if (data.length === 0) {
            container.innerHTML = `<p>No results available.</p>`;
            return;
        }

        data.forEach(item => {
            // Subject button
            const subjectBtn = document.createElement("button");
            subjectBtn.className = "subject-btn";
            subjectBtn.textContent = item.subjectName;

            // Section container
            const sectionBox = document.createElement("div");
            sectionBox.className = "section-box";

            item.sections.forEach(sec => {
                const secBtn = document.createElement("button");
                secBtn.className = "section-btnss";
                secBtn.textContent = `Section ${sec}`;

                secBtn.onclick = async () => {
                    try {
                     //   const response = await fetch("http://localhost:8081/Result/Result/FindUserResult", {
                        const response = await fetch("https://tierraagniveshacoaching.up.railway.app/Result/Result/FindUserResult", {
 
                            method: "POST",
                            headers: {
                                "Authorization": `Bearer ${userData.token}`,
                                "Content-Type": "application/json"
                            },
                            body: JSON.stringify({
                                page: 1,
                                size: 0,
                                chapterId: item.chapterId,
                                section: sec
                            })
                        });

                        if (!response.ok) {
                            throw new Error(`HTTP error! status: ${response.status}`);
                        }

                        const result = await response.json();
                        console.log("API Response for Section", sec, result);

                        // 👉 Open new window
                        const newWin = window.open("", "_blank");
                        newWin.document.write(`
                            <html>
                            <head>
                                <title>Result - ${result.subjectName} (Test ${result.section})</title>
                                <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
                                <script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/1.4.1/html2canvas.min.js"></script>
                                <style>
                                    body { font-family: Arial, sans-serif; margin: 20px; }
                                    h2 { margin-bottom: 10px; }
                                    .summary { margin-bottom: 20px; }
                                    .question-box {
                                        border: 1px solid #ccc;
                                        padding: 10px;
                                        margin-bottom: 10px;
                                        border-radius: 8px;
                                    }
                                    .correct { background-color: #e6ffe6; }
                                    .wrong { background-color: #ffe6e6; }
                                    .skipped { background-color: #f9f9f9; }
                                    #downloadBtn {
                                        background: #007BFF;
                                        color: white;
                                        padding: 8px 14px;
                                        border: none;
                                        border-radius: 6px;
                                        cursor: pointer;
                                        margin-bottom: 15px;
                                    }
                                </style>
                            </head>
                            <body>
                                <h2>${result.subjectName} - Test ${result.section}</h2>
                                <button id="downloadBtn">📥 Download PDF</button>
                                <div id="resultContent">
                                    <div class="summary">
                                        <p><strong>Score:</strong> ${result.score}</p>
                                        <p><strong>Correct:</strong> ${result.correctAnswers}, 
                                        <strong>Wrong:</strong> ${result.wrongAnswers}, 
                                        <strong>Skipped:</strong> ${result.skippedAnswers}</p>
                                        <p><strong>Percentage:</strong> ${result.percentage}</p>
                                    </div>
                                    <div id="questionList"></div>
                                </div>
                                <script>
    document.getElementById("downloadBtn").onclick = async () => {
        const { jsPDF } = window.jspdf;
        const pdf = new jsPDF("p", "pt", "a4");

        // 👉 capture only resultContent (not full body with buttons)
        const element = document.getElementById("resultContent");

        const canvas = await html2canvas(element, { scale: 2 });
        const imgData = canvas.toDataURL("image/png");

        const pageWidth = pdf.internal.pageSize.getWidth();
        const pageHeight = pdf.internal.pageSize.getHeight();

        const imgWidth = pageWidth;
        const imgHeight = (canvas.height * imgWidth) / canvas.width;

        let heightLeft = imgHeight;
        let position = 0;

        // first page
        pdf.addImage(imgData, "PNG", 0, position, imgWidth, imgHeight);
        heightLeft -= pageHeight;

        // additional pages
        while (heightLeft > 0) {
            position = heightLeft - imgHeight;
            pdf.addPage();
            pdf.addImage(imgData, "PNG", 0, position, imgWidth, imgHeight);
            heightLeft -= pageHeight;
        }

        pdf.save("result.pdf");
    };
</script>

                            </body>
                            </html>
                        `);

                        // Inject questions dynamically
                        const qList = newWin.document.getElementById("questionList");
                        result.resultModels.forEach((q, idx) => {
                            const qDiv = newWin.document.createElement("div");
                            qDiv.className = "question-box " + (q.correct ? "correct" : (q.skipped ? "skipped" : "wrong"));

                            qDiv.innerHTML = `
                                <p><strong>Q${idx + 1}:</strong> ${q.question}</p>
                                <p><strong>Your Answer:</strong> ${q.answered ? q.answered : "<em>Skipped</em>"}</p>
                                <p><strong>Correct Answer:</strong> ${q.correctAnswer}</p>
                            `;
                            qList.appendChild(qDiv);
                        });

                    } catch (error) {
                        console.error("Error fetching section result:", error);
                    }
                };

                sectionBox.appendChild(secBtn);
            });

            // Toggle expand/collapse
            subjectBtn.onclick = () => {
                sectionBox.classList.toggle("open");
            };

            container.appendChild(subjectBtn);
            container.appendChild(sectionBox);
        });

    } catch (error) {
        console.error("Error fetching result sections:", error);
        container.innerHTML = `<p style="color:red;">Failed to load results. Please try again later.</p>`;
    }
}


function logout() {
    alert("User Logged Out Successfully!!");
    const userData = JSON.parse(sessionStorage.getItem("userData"));
    const token = userData ? userData.token : null;

    try {
        if (token) {
           // fetch("http://localhost:8081/Auth/User/signout", {
            fetch("https://tierraagniveshacoaching.up.railway.app/Auth/User/signout", {
 
                method: "POST",
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            });
        }
    } catch (error) {
        console.error("Error calling signout API:", error);
    }

    sessionStorage.clear();
    localStorage.clear();
    window.location.href = "index.html";
}
