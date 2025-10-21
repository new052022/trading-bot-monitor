# Trading Monitor - React UI Development Guide

## 📋 Краткое резюме

Этот документ содержит инструкции для разработки React фронтенд-приложения для системы Trading Monitor.

## 🎯 Что будет реализовано

### 1. **Страницы приложения:**
   - ✅ Страница логина
   - ✅ Страница регистрации  
   - ✅ Личный кабинет (Dashboard)

### 2. **Функционал личного кабинета:**
   - **Статистика торговли:**
     - Общее количество сделок и PnL
     - Статистика за 30 дней
     - Статистика за 7 дней
   
   - **Таблица торговых позиций:**
     - Фильтры по датам и символам
     - Сортировка по колонкам
     - Пагинация
     - Экспорт в CSV
   
   - **Графики PnL:**
     - Визуализация прибыли/убытков
     - Переключение периодов (7/30/все дни)
   
   - **Статус торговой сессии:**
     - Реального времени индикатор
     - Автообновление каждые 30 секунд

### 3. **Технологический стек:**
   - React 18 + TypeScript
   - Vite (быстрая разработка)
   - Material-UI (красивый интерфейс)
   - React Query (кэширование данных)
   - React Router (навигация)
   - Axios (HTTP запросы с JWT)
   - Recharts (графики)
   - Tailwind CSS (стилизация)

## 🚀 Как использовать промпт

### Вариант 1: AI Chat (ChatGPT, Claude, etc.)
```
Просто скопируйте содержимое файла AI_AGENT_PROMPT_REACT_APP.md 
и вставьте в чат с AI ассистентом
```

### Вариант 2: AI Coding Agent (Cursor, Copilot, etc.)
```
1. Создайте новую папку для фронтенд проекта
2. Откройте в AI coding tool
3. Вставьте промпт и попросите создать проект
```

### Вариант 3: Передать файл напрямую
```
Отправьте файл AI_AGENT_PROMPT_REACT_APP.md агенту
```

## 📝 Пример использования с AI

### Промпт для AI агента:
```
Я прикрепил документ AI_AGENT_PROMPT_REACT_APP.md с полными 
требованиями к React приложению. 

Пожалуйста:
1. Создай полноценное React приложение согласно спецификации
2. Используй все указанные технологии
3. Реализуй все функции из документа
4. Создай подробную инструкцию по установке и запуску
5. Добавь примеры использования API

Начни с создания структуры проекта и установки зависимостей.
```

## 🔧 Backend API Endpoints (для справки)

### Аутентификация
```http
POST http://localhost:9011/api/auth/login
Content-Type: application/json

{
  "username": "user@example.com",
  "credentials": "password123"
}

Response:
{
  "token": "eyJhbGciOiJIUzI1NiIs...",
  "userId": 1,
  "username": "user@example.com",
  "expiresIn": 86400000
}
```

```http
POST http://localhost:9011/api/auth/register
Content-Type: application/json

{
  "username": "newuser@example.com",
  "credentials": "SecurePass123!"
}
```

### Trading Results
```http
GET http://localhost:9011/api/trading-results/{userId}/positions?startDate=2025-01-01&endDate=2025-10-19
Authorization: Bearer {token}

Response:
{
  "tradesNumber": 53,
  "realizedPnL": 22.56515754,
  "realizedPnL30Days": 22.56515754,
  "tradesNumber30Days": 53,
  "realizedPnL7Days": 22.56515754,
  "tradesNumber7Days": 53
}
```

### Trading Session
```http
GET http://localhost:9011/api/trading-session/{userId}/status
Authorization: Bearer {token}

Response:
{
  "sessionStatus": "ACTIVE",
  "totalBalance": 125.67937192,
  "isQuantActive": true,
  "isMarketActive": true,
  "isStrategyActive": true,
  "isOrderActive": true,
  "isStreamingActive": true,
  "isUserActive": true
}
```

## 📦 Ожидаемая структура проекта

```
trading-monitor-ui/
├── src/
│   ├── api/                    # API интеграция
│   │   ├── axios.config.ts     # Настройка Axios + JWT interceptors
│   │   ├── auth.api.ts         # Методы аутентификации
│   │   ├── trading.api.ts      # Методы торговли
│   │   └── session.api.ts      # Методы сессий
│   │
│   ├── components/             # React компоненты
│   │   ├── auth/
│   │   │   ├── LoginForm.tsx
│   │   │   └── RegisterForm.tsx
│   │   ├── dashboard/
│   │   │   ├── StatisticsCard.tsx
│   │   │   ├── TradingTable.tsx
│   │   │   ├── PnLChart.tsx
│   │   │   └── SessionStatus.tsx
│   │   └── common/
│   │       ├── Navbar.tsx
│   │       └── Loader.tsx
│   │
│   ├── context/
│   │   └── AuthContext.tsx     # Контекст аутентификации
│   │
│   ├── hooks/
│   │   ├── useAuth.ts
│   │   ├── useTradingData.ts
│   │   └── useSessionStatus.ts
│   │
│   ├── pages/
│   │   ├── LoginPage.tsx
│   │   ├── RegisterPage.tsx
│   │   └── DashboardPage.tsx
│   │
│   ├── routes/
│   │   ├── AppRoutes.tsx
│   │   └── ProtectedRoute.tsx  # Защита маршрутов
│   │
│   ├── types/
│   │   └── index.ts            # TypeScript типы
│   │
│   └── utils/
│       ├── storage.ts          # LocalStorage helpers
│       └── formatters.ts       # Форматирование данных
│
├── .env.development
├── .env.production
├── package.json
├── vite.config.ts
└── README.md
```

## 🔐 Безопасность JWT

AI агент должен реализовать:

1. **Сохранение токена:**
   ```typescript
   localStorage.setItem('authToken', token);
   ```

2. **Автоматическая отправка в заголовках:**
   ```typescript
   config.headers.Authorization = `Bearer ${token}`;
   ```

3. **Обработка 401 ошибок:**
   ```typescript
   if (error.response?.status === 401) {
     localStorage.removeItem('authToken');
     window.location.href = '/login';
   }
   ```

## 📊 Ключевые фичи UX

- ⏳ Loading состояния (скелетоны)
- ❌ Обработка ошибок (toast уведомления)
- ✅ Валидация форм
- 📱 Адаптивный дизайн (mobile-first)
- 🎨 Цветовая индикация (зеленый=прибыль, красный=убыток)
- 🔄 Автообновление данных
- 💾 Кэширование с React Query
- 🚀 Быстрая загрузка (code splitting)

## 🎯 Критерии успеха

После разработки приложение должно:

✅ Позволять пользователю логиниться/регистрироваться  
✅ Отображать все статистики из API  
✅ Показывать таблицу торговых позиций с фильтрами  
✅ Визуализировать PnL на графиках  
✅ Отображать статус сессии в реальном времени  
✅ Корректно обрабатывать все ошибки  
✅ Работать на мобильных устройствах  
✅ Иметь полную документацию  

## 📖 Что получите от AI агента

1. **Исходный код:**
   - Полностью рабочее приложение
   - TypeScript типы
   - Компоненты и хуки
   - API интеграция

2. **Документация:**
   - README.md с инструкциями
   - Описание архитектуры
   - Примеры использования
   - Гайд по деплою

3. **Конфигурация:**
   - package.json
   - vite.config.ts
   - .env.example
   - tsconfig.json

4. **Инструкции:**
   - Как установить
   - Как запустить
   - Как настроить
   - Как деплоить

## 🚀 Быстрый старт (после получения кода от AI)

```bash
# 1. Перейти в папку проекта
cd trading-monitor-ui

# 2. Установить зависимости
npm install

# 3. Создать .env файл
cp .env.example .env.development

# 4. Отредактировать .env
VITE_API_BASE_URL=http://localhost:9011

# 5. Запустить dev сервер
npm run dev

# Приложение будет доступно на http://localhost:5173
```

## 🐛 Troubleshooting

### CORS ошибки
Убедитесь, что в Spring Boot настроен CORS:
```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("*")
                .allowedHeaders("*");
    }
}
```

### 401 ошибки
Проверьте что JWT токен корректно отправляется в заголовке `Authorization: Bearer {token}`

## 💡 Советы

1. **Используйте React Query** для кэширования - это улучшит UX
2. **Добавьте Loading состояния** везде где идут API запросы
3. **Обрабатывайте все ошибки** с понятными сообщениями
4. **Делайте responsive дизайн** с самого начала
5. **Используйте TypeScript строго** - это поможет избежать багов

## 📞 Поддержка

Если AI агент не понял что-то из промпта, уточните:
- Конкретные API endpoints
- Формат данных (DTO)
- Дизайн предпочтения
- Дополнительные требования

---

**Готово к использованию!** 🎉

Передайте файл `AI_AGENT_PROMPT_REACT_APP.md` любому AI агенту и получите полноценное React приложение.
