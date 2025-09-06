const userData = JSON.parse(sessionStorage.getItem("userData"));
console.log("onlineTest.js loaded");
// Generic function to load subjects into a dropdown
async function loadSubjectsForDropdown(dropdownId, onSelectCallback) {
    const dropdown = document.querySelector(`#${dropdownId}`);
    const selected = dropdown.querySelector(".selected");
    const optionsContainer = dropdown.querySelector(".options");

    selected.textContent = "Loading...";
    optionsContainer.innerHTML = "";

    try {
      //  const response = await fetch("http://localhost:8081/MasterData/Subject/GetAll", {
        const response = await fetch("http://tierraagniveshacoaching.up.railway.app/MasterData/Subject/GetAll", {
            method: "POST",
            headers: {
                "Authorization": `Bearer ${userData.token}`,
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ page: 1, size: 0 })
        });

        if (!response.ok) throw new Error(`HTTP error! Status: ${response.status}`);

        const data = await response.json();
        const subjectList = data.Subjects || [];

        if (subjectList.length === 0) {
            selected.textContent = "No subjects found";
            return;
        }

        selected.textContent = "-- Choose Subject --";

        subjectList.forEach(subject => {
            const option = document.createElement("div");
            option.classList.add("option");
            option.textContent = subject.subjectName;
            option.dataset.value = subject.subjectId;

            option.addEventListener("click", () => {
                selected.textContent = subject.subjectName;
                dropdown.classList.remove("active");
                onSelectCallback(subject); // 🔥 call handler
            });

            optionsContainer.appendChild(option);
        });
    } catch (error) {
        console.error("Error fetching subjects:", error);
        selected.textContent = "Error loading subjects";
    }

    // Toggle dropdown open/close
    selected.addEventListener("click", () => {
        dropdown.classList.toggle("active");
    });

    // Close dropdown when clicking outside
    document.addEventListener("click", (e) => {
        if (!dropdown.contains(e.target)) dropdown.classList.remove("active");
    });
}

// Online Test handler
async function handleSubjectSelectForTests(subject) {
    displayTests(subject.subjectId);
}

// Init on page load
document.addEventListener("DOMContentLoaded", () => {
    loadSubjectsForDropdown("subjectDropdown", handleSubjectSelectForTests);     // For OnlineTest
});

// Display tests for selected subject
async function displayTests(subjectId) {
    const container = document.getElementById("testsContainer");
    container.innerHTML = "";

    if (!subjectId) {
        container.innerHTML = `<p>Please select a subject.</p>`;
        return;
    }

    try {
//        const response = await fetch("http://localhost:8081/Question/Question/FindSection", {
        const response = await fetch("http://tierraagniveshacoaching.up.railway.app/Question/Question/FindSection", {
            method: "POST",
            headers: {
                "Authorization": `Bearer ${userData.token}`,
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ subjectId })
        });

        if (!response.ok) throw new Error(`HTTP error! Status: ${response.status}`);

        const data = await response.json();
        const { sections, subjectName, chapterId } = data;

        if (!sections || sections.length === 0) {
            container.innerHTML = `<p>No sections found for subject "${subjectName}".</p>`;
            return;
        }

        sections.forEach(section => {
            const box = document.createElement("div");
            Object.assign(box.style, {
                border: "1px solid #ccc",
                padding: "15px",
                width: "200px",
                borderRadius: "8px",
                cursor: "pointer",
                margin: "10px",
                display: "inline-block",
                textAlign: "center",
                background: "#2e9031",
                color: "#fff"
            });

            box.innerHTML = `<h4>Test ${section}</h4><p>Click to open test</p>`;
            box.onclick = async () => {
                try {
 //                   const questionsRes = await fetch("http://localhost:8081/Question/Question/GetAll", {
                    const questionsRes = await fetch("http://tierraagniveshacoaching.up.railway.app/Question/Question/GetAll", {
 
                        method: "POST",
                        headers: {
                            "Authorization": `Bearer ${userData.token}`,
                            "Content-Type": "application/json"
                        },
                        body: JSON.stringify({
                            page: 1,
                            size: 0,
                            chapterId: chapterId,
                            section: section
                        })
                    });

                    if (!questionsRes.ok) throw new Error(`HTTP error! Status: ${questionsRes.status}`);

                    const questionsData = await questionsRes.json();

                    sessionStorage.setItem("mcqData", JSON.stringify({
                        subjectName,
                        section,
                        chapterId,
                        questions: questionsData
                    }));

                    window.location.href = "mcq.html";
                } catch (err) {
                    console.error("Error loading questions:", err);
                    alert("Failed to load questions. Please try again.");
                }
            };

            container.appendChild(box);
        });
    } catch (error) {
        console.error("Error fetching sections:", error);
        container.innerHTML = `<p style="color:red;">Failed to load tests. Please try again later.</p>`;
    }
}

async function loadWeeklyTests() {
    const userData = JSON.parse(localStorage.getItem("userData"));
    const container = document.getElementById("weeklyTestContainer");
    container.innerHTML = "<p>Loading...</p>";

    try {
    //    const response = await fetch("http://localhost:8081/Question/Question/GetAll", {
       const response = await fetch("hhttp://tierraagniveshacoaching.up.railway.app/Question/Question/GetAll", {
            method: "POST",
            headers: {
                "Authorization": `Bearer ${userData.token}`,
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                page: 1,
                size: 0,
                propertyName: "WeeklyTest"
            })
        });

        if (!response.ok) throw new Error(`HTTP error! Status: ${response.status}`);
        const data = await response.json();

        container.innerHTML = "";

        if (!data || !data.content || data.content.length === 0) {
            container.innerHTML = "<p>No Weekly Tests available.</p>";
            return;
        }

        data.content.forEach(test => {
            const box = document.createElement("div");
            box.className = "subject-box";
            box.innerHTML = `<h3>${test.testName || "Weekly Test"}</h3>
                             <p>${test.subjectName || "General"}</p>
                             <p>${test.section ? "Section " + test.section : ""}</p>`;

            // 🔥 On click -> directly save questions & redirect
            box.onclick = () => {
                sessionStorage.setItem("mcqData", JSON.stringify({
                    subjectName: test.subjectName,
                    section: test.section,
                    chapterId: test.chapterId,
                    questions: test.questions || [] // assuming API returns questions inside each test
                }));

                // Redirect to MCQ page
                window.location.href = "mcq.html";
            };

            container.appendChild(box);
        });
    } catch (err) {
        console.error("Error loading weekly tests:", err);
        container.innerHTML = "<p>Failed to load weekly tests. Please try again.</p>";
    }
}
