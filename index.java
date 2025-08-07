document.addEventListener('DOMContentLoaded', function() {
    // Mobile Menu Toggle
    const menuToggle = document.querySelector('.menu-toggle');
    const nav = document.querySelector('nav ul');
    
    menuToggle.addEventListener('click', function() {
        nav.classList.toggle('active');
    });
    
    // Close mobile menu when clicking on a link
    const navLinks = document.querySelectorAll('nav ul li a');
    navLinks.forEach(link => {
        link.addEventListener('click', function() {
            nav.classList.remove('active');
        });
    });
    
    // Hero Slider
    const slides = document.querySelectorAll('.slide');
    const dots = document.querySelectorAll('.dot');
    let currentSlide = 0;
    
    function showSlide(n) {
        slides.forEach(slide => slide.classList.remove('active'));
        dots.forEach(dot => dot.classList.remove('active'));
        
        slides[n].classList.add('active');
        dots[n].classList.add('active');
        currentSlide = n;
    }
    
    dots.forEach((dot, index) => {
        dot.addEventListener('click', () => showSlide(index));
    });
    
    // Auto slide change
    setInterval(() => {
        currentSlide = (currentSlide + 1) % slides.length;
        showSlide(currentSlide);
    }, 5000);
    
    // Smooth scrolling for anchor links
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function(e) {
            e.preventDefault();
            
            const targetId = this.getAttribute('href');
            const targetElement = document.querySelector(targetId);
            
            if (targetElement) {
                window.scrollTo({
                    top: targetElement.offsetTop - 80,
                    behavior: 'smooth'
                });
            }
        });
    });
    
    // Header scroll effect
    window.addEventListener('scroll', function() {
        const header = document.querySelector('header');
        header.classList.toggle('scrolled', window.scrollY > 50);
    });
    
    // Animate stats on scroll
    const statItems = document.querySelectorAll('.stat-item h4');
    const statBars = document.querySelectorAll('.bar-fill');
    
    function animateStats() {
        statItems.forEach(item => {
            const target = parseInt(item.getAttribute('data-target'));
            let count = 0;
            const increment = target / 50;
            
            const timer = setInterval(() => {
                count += increment;
                if (count >= target) {
                    count = target;
                    clearInterval(timer);
                }
                item.textContent = Math.floor(count);
            }, 20);
        });
        
        statBars.forEach(bar => {
            const percent = bar.getAttribute('data-percent');
            bar.style.width = percent + '%';
        });
    }
    
    // Intersection Observer for animations
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                if (entry.target.classList.contains('stats')) {
                    animateStats();
                }
                
                if (entry.target.classList.contains('operations-map')) {
                    // Map tooltip functionality
                    const areas = document.querySelectorAll('map area');
                    const tooltip = document.getElementById('map-tooltip');
                    
                    areas.forEach(area => {
                        area.addEventListener('mouseover', function(e) {
                            const title = this.getAttribute('title');
                            tooltip.textContent = title + ' Operations';
                            tooltip.style.opacity = '1';
                            
                            // Position tooltip near cursor
                            const rect = this.getBoundingClientRect();
                            tooltip.style.left = (rect.left + window.scrollX) + 'px';
                            tooltip.style.top = (rect.top + window.scrollY - 40) + 'px';
                        });
                        
                        area.addEventListener('mouseout', function() {
                            tooltip.style.opacity = '0';
                        });
                    });
                }
                
                entry.target.classList.add('animated');
                observer.unobserve(entry.target);
            }
        });
    }, { threshold: 0.1 });
    
    // Observe elements that need animation
    document.querySelectorAll('.stats, .operations-map').forEach(el => {
        observer.observe(el);
    });
    
    // Contact Form Submission
    const contactForm = document.getElementById('contactForm');
    const formMessage = document.getElementById('form-message');
    
    contactForm.addEventListener('submit', function(e) {
        e.preventDefault();
        
        // Get form values
        const name = document.getElementById('name').value;
        const email = document.getElementById('email').value;
        const subject = document.getElementById('subject').value;
        const message = document.getElementById('message').value;
        
        // Simple validation
        if (name === '' || email === '' || message === '') {
            formMessage.textContent = 'Please fill in all required fields.';
            formMessage.classList.remove('success');
            formMessage.classList.add('error');
            formMessage.style.display = 'block';
            return;
        }
        
        // Here you would typically send the form data to a server
        // For this example, we'll just simulate a successful submission
        setTimeout(() => {
            formMessage.textContent = 'Thank you for your message