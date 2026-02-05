function openMasterData(type) {
  // Hide other screens
  document.querySelectorAll('.screen').forEach(screen => {
    screen.classList.remove('active-screen');
  });

  // Show Master Data screen
  const screen = document.getElementById('masterdata');
  screen.classList.add('active-screen');

  // Update heading
  document.getElementById('masterdata-title').textContent =
    "Master Data — " + type.charAt(0).toUpperCase() + type.slice(1);

  // Load table content
  loadMasterTable(type);
}

function loadMasterTable(type) {
  const container = document.getElementById('masterdata-table-container');

  // Dummy data (you can replace with API later)
  let data = [];
  if (type === 'roles') {
    data = [
      { id: 1, roleName: "Admin" },
      { id: 2, roleName: "Teacher" },
      { id: 3, roleName: "Student" }
    ];
  } else if (type === 'subjects') {
    data = [
      { id: 1, name: "Math" },
      { id: 2, name: "Physics" },
      { id: 3, name: "Chemistry" }
    ];
  } else if (type === 'questions') {
    data = [
      { id: 101, question: "What is H2O?", subject: "Chemistry" },
      { id: 102, question: "Define gravity", subject: "Physics" }
    ];
  } else if (type === 'users') {
    data = [
      { id: 1, name: "Ravi", email: "ravi@example.com", role: "Student" },
      { id: 2, name: "Priya", email: "priya@example.com", role: "Teacher" }
    ];
  }

  // Build HTML table dynamically
  if (data.length === 0) {
    container.innerHTML = "<p>No records found.</p>";
    return;
  }

  const headers = Object.keys(data[0]);
  let html = "<table class='data-table'><thead><tr>";

  headers.forEach(h => html += `<th>${h}</th>`);
  html += "</tr></thead><tbody>";

  data.forEach(row => {
    html += "<tr>";
    headers.forEach(h => html += `<td>${row[h]}</td>`);
    html += "</tr>";
  });

  html += "</tbody></table>";

  container.innerHTML = html;
}
