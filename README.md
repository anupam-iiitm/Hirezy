<div align="center">
  <img src="https://raw.githubusercontent.com/tandpfun/skill-icons/main/icons/React-Dark.svg" alt="React" width="40" height="40" />
  <img src="https://raw.githubusercontent.com/tandpfun/skill-icons/main/icons/Spring-Dark.svg" alt="Spring" width="40" height="40" />
  <img src="https://raw.githubusercontent.com/tandpfun/skill-icons/main/icons/PostgreSQL-Dark.svg" alt="Postgres" width="40" height="40" />
  <img src="https://raw.githubusercontent.com/tandpfun/skill-icons/main/icons/Docker.svg" alt="Docker" width="40" height="40" />
  <img src="https://raw.githubusercontent.com/tandpfun/skill-icons/main/icons/TailwindCSS-Dark.svg" alt="Tailwind" width="40" height="40" />
  
  <br />
  <br />

  <h1>🚀 Hirezy - AI Job Portal System</h1>

  <p>
    <strong>A next-generation, scalable microservices-based job portal powered by Artificial Intelligence.</strong>
  </p>
  
  <p>
    <a href="#features">✨ Features</a> •
    <a href="#architecture">🏗️ Architecture</a> •
    <a href="#tech-stack">💻 Tech Stack</a> •
    <a href="#getting-started">🚀 Getting Started</a> •
    <a href="#contributing">🤝 Contributing</a>
  </p>
</div>

<hr />

## ✨ Features

- 🧠 **AI-Powered Insights**: Smart resume parsing and job matching leveraging AI.
- 🏢 **Company & Job Management**: Robust portals for companies to manage postings and applicants.
- 🧑‍💼 **Candidate Profiles**: Comprehensive resume and profile builder for job seekers.
- 🔔 **Real-Time Notifications**: Stay updated on applications and job alerts.
- 🔐 **Secure Authentication**: JWT-based secure user authentication and authorization.
- 📊 **Interactive Dashboards**: Data-rich, interactive charts built with Recharts.

---

## 🏗️ Architecture

Hirezy is built on a robust **Microservices Architecture** ensuring scalability, fault tolerance, and separation of concerns.

### Backend Microservices
- `job-portal-user-service`: User authentication and account management.
- `job-portal-resume-service`: Candidate resume building and parsing.
- `job-portal-company-service`: Employer profiles and management.
- `job-portal-job-service`: Job listings, searching, and filtering.
- `job-portal-application-service`: End-to-end job application tracking.
- `job-portal-notification-service`: Email and in-app notifications.
- `job-portal-ai-service`: AI integrations for smart matching.
- `job-portal-preferences`: User settings and preference management.

---

## 💻 Tech Stack

### Frontend
- **Framework**: [React 19](https://react.dev/) + [Vite](https://vitejs.dev/)
- **Styling**: [Tailwind CSS 4](https://tailwindcss.com/) + [shadcn/ui](https://ui.shadcn.com/)
- **State Management**: [Redux Toolkit](https://redux-toolkit.js.org/)
- **Forms & Validation**: React Hook Form + Zod

### Backend
- **Framework**: [Spring Boot (Java)](https://spring.io/projects/spring-boot)
- **Architecture**: Microservices
- **Database**: [PostgreSQL 16](https://www.postgresql.org/)

### DevOps & Infrastructure
- **Containerization**: [Docker](https://www.docker.com/) & Docker Compose

---

## 🚀 Getting Started

### 📋 Prerequisites
Make sure you have the following installed:
- **Java 17+**
- **Node.js (v18+)** & **pnpm**
- **Maven**
- **Docker** & **Docker Compose**

### 🛠️ Installation & Setup

#### 1. Clone the repository
```bash
git clone https://github.com/anupam-iiitm/Hirezy.git
cd Hirezy
```

#### 2. Start the Databases
The system uses isolated PostgreSQL databases for different services.
```bash
cd docker
docker-compose up -d
```

#### 3. Run Backend Services
You will need to run the required microservices. From the root directory:
```bash
cd job-portal-system/services/job-portal-user-service
mvn spring-boot:run
# Repeat for other essential services (resume, job, company, etc.)
```

#### 4. Run the Frontend Client
```bash
cd job-portal-frontend
pnpm install
pnpm dev
```
Navigate to `http://localhost:5173` in your browser to see the application!

---

## 🤝 Contributing

Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

<div align="center">
  <p>Made with ❤️ for the community.</p>
</div>
