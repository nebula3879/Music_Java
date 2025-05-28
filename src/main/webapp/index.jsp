<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Музыкальное приложение</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600;700&family=Playfair+Display:wght@700&display=swap" rel="stylesheet">
    <style>
        :root {
            --primary: #6C5CE7;
            --primary-light: #A29BFE;
            --secondary: #00B894;
            --accent: #FD79A8;
            --dark: #2D3436;
            --light: #F5F6FA;
        }

        body {
            margin: 0;
            padding: 0;
            font-family: 'Montserrat', sans-serif;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            background: linear-gradient(-45deg, #1a2a6c, #b21f1f, #0f2027, #134e4a);
            background-size: 400% 400%;
            animation: backgroundShift 20s ease infinite;
            color: var(--light);
            line-height: 1.6;
        }

        @keyframes backgroundShift {
            0% { background-position: 0% 50%; }
            50% { background-position: 100% 50%; }
            100% { background-position: 0% 50%; }
        }

        .container {
            flex: 1;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            padding: 4rem 2rem;
            max-width: 1200px;
            margin: 0 auto;
            text-align: center;
        }

        .welcome-title {
            font-family: 'Playfair Display', serif;
            font-size: 4rem;
            margin-bottom: 1.5rem;
            font-weight: 700;
            color: white;
            line-height: 1.2;
            text-shadow: 0 2px 10px rgba(0,0,0,0.3);
            position: relative;
        }

        .welcome-subtitle {
            font-size: 1.4rem;
            margin-bottom: 3rem;
            max-width: 700px;
            color: rgba(255,255,255,0.9);
            font-weight: 400;
        }

        .button-group {
            display: flex;
            gap: 2rem;
            flex-wrap: wrap;
            justify-content: center;
        }

        .btn {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            gap: 1rem;
            padding: 1.2rem 2.8rem;
            font-weight: 600;
            border-radius: 50px;
            cursor: pointer;
            transition: all 0.4s cubic-bezier(0.25, 0.8, 0.25, 1);
            text-decoration: none;
            font-size: 1.1rem;
            position: relative;
            overflow: hidden;
            z-index: 1;
            color: white;
            border: none;
            letter-spacing: 0.5px;
            min-width: 240px;
            box-shadow: 0 5px 25px rgba(0,0,0,0.2);
        }

        .btn-primary {
            background: linear-gradient(135deg, var(--primary), var(--primary-light));
        }

        .btn-secondary {
            background: linear-gradient(135deg, var(--secondary), #55EFC4);
        }

        .btn-accent {
            background: linear-gradient(135deg, var(--accent), #FF7675);
        }

        .btn::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: linear-gradient(135deg, rgba(255,255,255,0.2), rgba(255,255,255,0));
            opacity: 0;
            transition: opacity 0.3s ease;
            z-index: -1;
        }

        .btn:hover {
            transform: translateY(-5px);
            box-shadow: 0 15px 35px rgba(0,0,0,0.3);
        }

        .btn:hover::before {
            opacity: 1;
        }

        .btn i {
            font-size: 1.3rem;
            transition: transform 0.3s ease;
        }

        .btn:hover i {
            transform: translateX(5px);
        }

        @media (max-width: 768px) {
            .navbar {
                padding: 1rem;
                flex-direction: column;
                gap: 1rem;
            }

            .nav-links {
                width: 100%;
                justify-content: space-around;
                gap: 0.5rem;
            }

            .welcome-title {
                font-size: 2.5rem;
                margin-bottom: 1rem;
            }

            .welcome-subtitle {
                font-size: 1.1rem;
                margin-bottom: 2.5rem;
                padding: 0 1rem;
            }

            .button-group {
                flex-direction: column;
                gap: 1.5rem;
                width: 100%;
            }

            .btn {
                width: 100%;
                min-width: auto;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <h3 class="welcome-title">Добро пожаловать в MelodyFlow</h3>
    <p class="welcome-subtitle">Ваш персональный музыкальный менеджер для управления треками, плейлистами и пользователями</p>

    <div class="button-group">
        <a href="${pageContext.request.contextPath}/users" class="btn btn-primary">
            <i class="fas fa-users"></i> Пользователи
        </a>
        <a href="${pageContext.request.contextPath}/tracks" class="btn btn-secondary">
            <i class="fas fa-music"></i> Треки
        </a>
        <a href="${pageContext.request.contextPath}/playlists" class="btn btn-accent">
            <i class="fas fa-list"></i> Плейлисты
        </a>
    </div>
</div>
</body>
</html>