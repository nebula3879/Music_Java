<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.music.model.Playlist" %>
<%@ page import="com.music.model.User" %>
<%@ page import="com.music.model.Track" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Редактировать плейлист</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css ">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat :wght@400;500;600;700&family=Playfair+Display:wght@700&display=swap" rel="stylesheet">
    <style>
        :root {
            --primary: #FF7675;
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

        .navbar {
            background-color: rgba(45, 52, 54, 0.9);
            padding: 1.5rem 3rem;
            display: flex;
            align-items: center;
            justify-content: space-between;
            box-shadow: 0 4px 30px rgba(0,0,0,0.2);
        }

        .navbar-brand {
            color: var(--light);
            font-family: 'Playfair Display', serif;
            font-size: 1.8rem;
            font-weight: 700;
            text-decoration: none;
            display: flex;
            align-items: center;
            gap: 0.8rem;
            letter-spacing: 0.5px;
        }

        .nav-links {
            display: flex;
            gap: 2rem;
        }

        .nav-link {
            color: var(--light);
            text-decoration: none;
            font-weight: 500;
            transition: all 0.3s ease;
            display: flex;
            align-items: center;
            gap: 0.6rem;
            font-size: 1.1rem;
            opacity: 0.9;
        }

        .nav-link:hover,
        .nav-link.active {
            color: var(--accent);
            transform: translateY(-2px);
            opacity: 1;
        }

        .container {
            flex: 1;
            padding: 2rem;
            max-width: 1200px;
            margin: 0 auto;
            width: 100%;
        }

        .page-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 2rem;
            flex-wrap: wrap;
            gap: 1rem;
        }

        .page-title {
            font-family: 'Playfair Display', serif;
            font-size: 2.5rem;
            margin: 0;
            display: flex;
            align-items: center;
            gap: 1rem;
            color: white;
        }

        .form-container {
            background: rgba(45, 52, 54, 0.8);
            padding: 2rem;
            border-radius: 12px;
            box-shadow: 0 8px 32px rgba(0,0,0,0.2);
            margin-bottom: 2rem;
            backdrop-filter: blur(8px);
        }

        .form-row {
            display: flex;
            gap: 1.5rem;
            flex-wrap: wrap;
            margin-bottom: 1.5rem;
        }

        .form-group {
            flex: 1;
            min-width: 250px;
        }

        .form-label {
            display: block;
            margin-bottom: 0.75rem;
            font-weight: 600;
            color: rgba(255,255,255,0.9);
        }

        .form-control {
            width: 100%;
            padding: 1rem 1.25rem;
            background: rgba(30, 30, 30, 0.7);
            border: 1px solid rgba(255,255,255,0.1);
            border-radius: 8px;
            color: var(--light);
            transition: all 0.3s ease;
            font-size: 1rem;
        }

        .form-control:focus {
            outline: none;
            border-color: var(--primary);
            box-shadow: 0 0 0 3px rgba(108, 92, 231, 0.3);
        }

        .btn {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            gap: 1rem;
            padding: 1rem 2rem;
            font-weight: 600;
            border-radius: 50px;
            cursor: pointer;
            transition: all 0.4s cubic-bezier(0.25, 0.8, 0.25, 1);
            text-decoration: none;
            font-size: 1rem;
            position: relative;
            overflow: hidden;
            z-index: 1;
            color: white;
            border: none;
            letter-spacing: 0.5px;
            min-width: 180px;
            box-shadow: 0 5px 25px rgba(0,0,0,0.2);
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
            font-size: 1.2rem;
            transition: transform 0.3s ease;
        }

        .btn:hover i {
            transform: translateX(5px);
        }

        .btn-primary {
            background: linear-gradient(135deg, var(--primary), var(--primary-light));
        }

        .btn-secondary {
            background: linear-gradient(135deg, #e74c3c, #c0392b);
        }

        .btn-sm {
            padding: 0.75rem 1.5rem;
            min-width: auto;
            font-size: 0.9rem;
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

            .container {
                padding: 1.5rem;
            }

            .page-title {
                font-size: 2rem;
            }

            .form-row {
                flex-direction: column;
                gap: 1.5rem;
            }

            .table th,
            .table td {
                padding: 1rem;
            }

            .btn {
                width: 100%;
                min-width: auto;
            }
        }
    </style>
</head>
<body>
<div class="navbar">
    <a href="${pageContext.request.contextPath}/" class="navbar-brand">
        <i class="fas fa-music"></i> MelodyFlow
    </a>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/users" class="nav-link">
            <i class="fas fa-users"></i> Пользователи
        </a>
        <a href="${pageContext.request.contextPath}/tracks" class="nav-link">
            <i class="fas fa-music"></i> Треки
        </a>
        <a href="${pageContext.request.contextPath}/playlists" class="nav-link active">
            <i class="fas fa-list"></i> Плейлисты
        </a>
    </div>
</div>

<div class="container">
    <div class="page-header">
        <h1 class="page-title"><i class="fas fa-edit"></i> Редактировать плейлист</h1>
    </div>

    <div class="form-container">
        <form action="${pageContext.request.contextPath}/playlists" method="post">
            <input type="hidden" name="action" value="edit">
            <input type="hidden" name="id" value="${playlist.id}">

            <div class="form-row">
                <div class="form-group">
                    <label class="form-label">Пользователь</label>
                    <select name="userId" class="form-control" required>
                        <option value="">-- Выберите пользователя --</option>
                        <% for (User user : (List<User>) request.getAttribute("users")) { %>
                        <option value="<%= user.getId() %>" <%= ((Playlist) request.getAttribute("playlist")).getUserId() == user.getId() ? "selected" : "" %>>
                            <%= user.getName() %>
                        </option>
                        <% } %>
                    </select>
                </div>
                <div class="form-group">
                    <label class="form-label">Трек</label>
                    <select name="trackId" class="form-control" required>
                        <option value="">-- Выберите трек --</option>
                        <% for (Track track : (List<Track>) request.getAttribute("tracks")) { %>
                        <option value="<%= track.getId() %>" <%= ((Playlist) request.getAttribute("playlist")).getTrackId() == track.getId() ? "selected" : "" %>>
                            <%= track.getTitle() %> - <%= track.getArtist() %>
                        </option>
                        <% } %>
                    </select>
                </div>
            </div>

            <div class="form-row">
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-save"></i> Сохранить
                </button>
                <a href="${pageContext.request.contextPath}/playlists" class="btn btn-secondary">
                    <i class="fas fa-times"></i> Отмена
                </a>
            </div>
        </form>
    </div>
</div>
</body>
</html>