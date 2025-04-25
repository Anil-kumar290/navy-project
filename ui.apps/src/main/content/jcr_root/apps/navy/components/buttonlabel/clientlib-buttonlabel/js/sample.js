document.addEventListener("DOMContentLoaded", function () {
  const button = document.getElementById("gorest-fetch-btn");
  const output = document.getElementById("gorest-users-output");

  if (!button || !output) return;

  button.addEventListener("click", function () {
    fetch("/services/gorest/users")
      .then(response => response.json())
      .then(users => {
        if (!Array.isArray(users)) {
          output.innerHTML = "<p>Invalid data format received.</p>";
          return;
        }

        output.innerHTML = users.map(user => `
          <div class="user-card">
            <h4>${user.name || "Unknown"}</h4>
            <p><strong>Email:</strong> ${user.email || "N/A"}</p>
            <p><strong>Gender:</strong> ${user.gender || "-"}</p>
            <p><strong>Status:</strong> ${user.status || "-"}</p>
          </div>
        `).join("");
      })
      .catch(error => {
        console.error("Fetch error:", error);
        output.innerHTML = "<p style='color:red;'>Error loading user data.</p>";
      });
  });
});
