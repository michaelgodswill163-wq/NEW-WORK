// User data and authentication
const users = [
    // Sample users (in a real app, this would be in a database)
    { id: 1, username: 'user1', email: 'user1@example.com', password: 'password1', balance: 100.00 },
    { id: 2, username: 'user2', email: 'user2@example.com', password: 'password2', balance: 200.00 }
];

let currentUser = null;

// DOM Elements
const mainContent = document.getElementById('main-content');
const userInfo = document.getElementById('user-info');
const usernameDisplay = document.getElementById('username-display');
const logoutBtn = document.getElementById('logout-btn');
const authButtons = document.getElementById('auth-buttons');
const accountBalance = document.getElementById('account-balance');
const balanceElement = document.getElementById('balance');
const loginError = document.getElementById('login-error');
const registerError = document.getElementById('register-error');

// Initialize the app
function init() {
    checkAuthStatus();
    setupEventListeners();
}

// Check if user is logged in
function checkAuthStatus() {
    const token = localStorage.getItem('authToken');
    const userId = localStorage.getItem('userId');
    
    if (token && userId) {
        const user = users.find(u => u.id === parseInt(userId));
        if (user) {
            loginUser(user);
        }
    } else {
        showPublicContent();
    }
}

// Login user
function loginUser(user) {
    currentUser = user;
    localStorage.setItem('authToken', 'sample-token');
    localStorage.setItem('userId', user.id);
    updateUIAfterLogin();
    loadSportsContent();
}

// Logout user
function logoutUser() {
    currentUser = null;
    localStorage.removeItem('authToken');
    localStorage.removeItem('userId');
    showPublicContent();
}

// Update UI after login
function updateUIAfterLogin() {
    authButtons.style.display = 'none';
    userInfo.style.display = 'flex';
    usernameDisplay.textContent = currentUser.username;
    accountBalance.style.display = 'block';
    balanceElement.textContent = currentUser.balance.toFixed(2);
}

// Show content for public users
function showPublicContent() {
    authButtons.style.display = 'block';
    userInfo.style.display = 'none';
    accountBalance.style.display = 'none';
    mainContent.innerHTML = `
        <div class="login-message">
            <h2>Welcome to SportyBet</h2>
            <p>Please login or register to start betting</p>
        </div>
    `;
}

// Load sports content for logged in users
function loadSportsContent() {
    mainContent.innerHTML = `
        <div class="sports-menu">
            <button class="sport-btn active" data-sport="football">Football</button>
            <button class="sport-btn" data-sport="basketball">Basketball</button>
            <button class="sport-btn" data-sport="tennis">Tennis</button>
            <button class="sport-btn" data-sport="baseball">Baseball</button>
            <button class="sport-btn" data-sport="hockey">Hockey</button>
        </div>

        <div class="events-container">
            <h2 id="current-sport">Football Events</h2>
            <div class="events-list" id="events-list">
                <!-- Events will be loaded here by JavaScript -->
            </div>
        </div>

        <div class="bet-slip">
            <h3>Bet Slip <span id="bet-count">(0)</span></h3>
            <div class="bets" id="bets-container">
                <!-- Bets will be added here -->
                <p class="empty-message">No bets added yet</p>
            </div>
            <div class="bet-total">
                <p>Total Odds: <span id="total-odds">1.00</span></p>
                <p>Potential Win: $<span id="potential-win">0.00</span></p>
                <input type="number" id="stake-amount" placeholder="Stake amount" min="1">
                <button id="place-bet-btn">Place Bet</button>
            </div>
        </div>
    `;
    
    // Initialize sports betting functionality
    initializeSportsBetting();
}

// Initialize sports betting functionality
function initializeSportsBetting() {
    // ... (all the sports betting code from the previous implementation goes here)
    // This includes the eventsData, loadEvents, bet slip functionality, etc.
    // Just make sure to update the balance display to use currentUser.balance
    
    // Example update to the placeBet function:
    const placeBetBtn = document.getElementById('place-bet-btn');
    if (placeBetBtn) {
        placeBetBtn.addEventListener('click', () => {
            const stake = parseFloat(stakeAmount.value);
            
            if (stake > currentUser.balance) {
                alert('Insufficient balance');
                return;
            }
            
            // Deduct stake from balance
            currentUser.balance -= stake;
            balanceElement.textContent = currentUser.balance.toFixed(2);
            
            // ... rest of the place bet logic
        });
    }
}

// Setup event listeners
function setupEventListeners() {
    // Login form
    document.getElementById('login-form').addEventListener('submit', (e) => {
        e.preventDefault();
        loginError.textContent = '';
        
        const username = document.getElementById('login-username').value;
        const password = document.getElementById('login-password').value;
        
        const user = users.find(u => u.username === username && u.password === password);
        
        if (user) {
            loginUser(user);
            document.getElementById('login-modal').style.display = 'none';
            document.getElementById('login-form').reset();
        } else {
            loginError.textContent = 'Invalid username or password';
        }
    });
    
    // Register form
    document.getElementById('register-form').addEventListener('submit', (e) => {
        e.preventDefault();
        registerError.textContent = '';
        
        const username = document.getElementById('register-username').value;
        const email = document.getElementById('register-email').value;
        const password = document.getElementById('register-password').value;
        const confirmPassword = document.getElementById('register-confirm-password').value;
        
        // Validate
        if (password !== confirmPassword) {
            registerError.textContent = 'Passwords do not match';
            return;
        }
        
        if (users.some(u => u.username === username)) {
            registerError.textContent = 'Username already taken';
            return;
        }
        
        if (users.some(u => u.email === email)) {
            registerError.textContent = 'Email already registered';
            return;
        }
        
        // Create new user
        const newUser = {
            id: users.length + 1,
            username,
            email,
            password,
            balance: 50.00 // Starting balance for new users
        };
        
        users.push(newUser);
        loginUser(newUser);
        document.getElementById('register-modal').style.display = 'none';
        document.getElementById('register-form').reset();
    });
    
    // Logout button
    logoutBtn.addEventListener('click', logoutUser);
    
    // Modal buttons (keep these from previous implementation)
    loginBtn.addEventListener('click', () => {
        document.getElementById('login-modal').style.display = 'block';
    });
    
    registerBtn.addEventListener('click', () => {
        document.getElementById('register-modal').style.display = 'block';
    });
    
    // Close buttons
    document.querySelectorAll('.close-btn').forEach(button => {
        button.addEventListener('click', () => {
            document.getElementById('login-modal').style.display = 'none';
            document.getElementById('register-modal').style.display = 'none';
        });
    });
    
    // Close modals when clicking outside
    window.addEventListener('click', (e) => {
        if (e.target === document.getElementById('login-modal')) {
            document.getElementById('login-modal').style.display = 'none';
        }
        if (e.target === document.getElementById('register-modal')) {
            document.getElementById('register-modal').style.display = 'none';
        }
    });
}

// Initialize the application
init();