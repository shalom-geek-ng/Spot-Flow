# 🚀 SpotFlow URL Shortener API Documentation

**Developed by:** [Shalom Oluwafemi]

**Deployed on Railway:** 🔗 [https://spot-flow-production.up.railway.app](https://spot-flow-production.up.railway.app)

---

## 🧠 Overview


### ✨ Key Features Implemented

✅ Enter a valid URL to be shortened  
✅ The shortened version has a configurable **TTL (Time-To-Live)**  
✅ The shortened link **redirects to the original URL** when accessed  
✅ Each shortened URL **tracks click counts**  
✅ Authenticated users can **view analytics** (URLs they’ve created + number of clicks)  
✅ Logged-in users can **choose their own short code**, instead of a generated one  

---

## 🧭 Base URL
https://spot-flow-production.up.railway.app


🔹 1️⃣ Feature — Enter a Valid URL to Be Shortened & Set TTL
Description
Accepts a valid URL and returns a shortened code. Default TTL = 24 hours. URL must start with http or https.

Endpoint

Method: POST

URL: /api/v1/create-url

Request headers
Content-Type: application/json


(If authenticated flows are used for other endpoints, include Authorization: Bearer <token> — not required for anonymous shortening.)

Request body (JSON)
{
  "originalUrl": "https://www.example.com/my-awesome-article"
}

Example cURL
curl -X POST "https://spot-flow-production.up.railway.app/api/v1/create-url" \
  -H "Content-Type: application/json" \
  -d '{"originalUrl":"https://www.example.com/my-awesome-article"}'

Postman

Select POST → https://<base>/api/v1/create-url

Headers: Content-Type: application/json

Body → raw → JSON:

{
  "originalUrl": "https://www.example.com/my-awesome-article"
}

Success response

Status: 201 Created

Body:

{
  "originalUrl": "https://www.example.com/my-awesome-article",
  "shortCode": "a1B2c3",
  "createdAt": "2025-10-08T12:45:00",
  "expiresAt": "2025-10-09T12:45:00",
  "clickCount": 0
}

Validation / errors

If originalUrl is missing or invalid (doesn’t start with http/https), the endpoint returns a 4xx error (e.g., 400 Bad Request) with an error message like:

{ "message": "Enter a valid URL to be shortened" }


🔹 2️⃣ Feature — Redirect to Original URL and Count Increase

Description
When a shortened link is accessed, the system fetches its corresponding original URL, checks if it has expired, and tracks the number of clicks. If valid, it redirects users to the original URL.

Endpoint

Method: GET

URL: /api/v1/get-original-url-by-shortened/{shortUrl}

Path Parameter
Name	Type	Required	Description
shortUrl	String	✅	The shortened code generated for the URL.
Example Request
GET https://spot-flow-production.up.railway.app/api/v1/get-original-url-by-shortened/a1B2c3

Success Response

Status: 200 OK

Body:

{
  "originalUrl": "https://www.example.com/my-awesome-article",
  "createdAt": "2025-10-08T12:45:00",
  "expiresAt": "2025-10-09T12:45:00"
}

Behavior

If the URL has expired → returns 404 Not Found

{ "message": "This short URL has expired" }


Each visit automatically increments the click count for analytics.


🔹 3️⃣ Feature — View URL Analytics (Authenticated Users Only)
📝 Description

Authenticated users can view analytics for all the URLs they’ve created.
The response includes each shortened URL, its original link, click count, creation date, and expiry date.

If the user is not signed in, they’ll receive a clear error message asking them to sign up.

🔧 Endpoint

Method: GET

URL: /api/v1/get-url-analytics

Authentication: ✅ Required (Bearer Token)

📩 Request Headers
Header	Value
Content-Type	application/json
Authorization	Bearer <your_jwt_token>
💻 Example cURL
curl -X GET "https://spot-flow-production.up.railway.app/api/v1/get-url-analytics" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..." \
  -H "Content-Type: application/json"

🧪 Postman

Method: GET → https://<base>/api/v1/get-url-analytics

Headers:

Content-Type: application/json

Authorization: Bearer <your_jwt_token>

✅ Success Response

Status: 200 OK

{
  "userId": 1,
  "urls": [
    {
      "originalUrl": "https://www.example.com/my-awesome-article",
      "shortUrl": "a1B2c3",
      "count": 12,
      "createdAt": "2025-10-08T12:45:00",
      "expiresAt": "2025-10-09T12:45:00"
    },
    {
      "originalUrl": "https://www.github.com/spotflow",
      "shortUrl": "b2X9kL",
      "count": 4,
      "createdAt": "2025-10-07T11:10:00",
      "expiresAt": "2025-10-08T11:10:00"
    }
  ]
}

⚠️ Error Responses

If the user is not authenticated:

{ "message": "It's only authenticated users that have this privilege, please consider signing up." }


If the user has no shortened URLs yet:

{ "message": "You haven't created any shortened URLs yet." }

4️⃣ Authenticated Users Can Create Custom Short URLs
📘 Description

Authenticated users can choose their own short code (e.g., https://short.ly/mybrand) instead of a randomly generated one.
This adds personalization and brand control for logged-in users.

📍 Endpoint

POST /v1/create-personal-short-url

🧾 Request Body
{
  "originalUrl": "https://example.com/my-page",
  "shortCode": "mybrand"
}

🔁 Example Response
{
  "originalUrl": "https://example.com/my-page",
  "shortCode": "mybrand",
  "createdAt": "2025-10-08T10:23:45",
  "expiresAt": "2025-10-15T10:23:45",
  "clickCount": 0
}

⚙️ Controller Snippet
@PostMapping("/v1/create-personal-short-url")
public ResponseEntity<?> createPersonalShortUrl(@RequestBody UrlCreatorDto dto) {
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(urlService.createPersonalShortCode(dto));
}

🧠 Highlights

✅ Only authenticated users can access this feature

⚡ Custom short codes must be unique

🕒 Personal links expire after 7 days

🚫 Throws descriptive errors for invalid input

5️⃣ 👤 User Signup (Create Account)
📘 Description

This endpoint allows new users to sign up by providing an email and password.
Registered users can then log in to create personalized short URLs and view their analytics.

📍 Endpoint

POST /api/v1/create-user

🧾 Request Body
{
  "email": "user@example.com",
  "password": "securePassword123"
}

🔁 Example Response
{
  "email": "user@example.com"
}

⚙️ Controller Snippet
@PostMapping("/v1/create-user")
public ResponseEntity<?> createUser(@RequestBody UserDto dto) {
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(userService.createNewUser(dto));
}

🧠 Highlights

✅ Registers new users into the system

🔒 Passwords are securely encoded before saving

⚠️ Returns a clear error message if a user already exists

🌐 Users can later authenticate to access premium features (analytics, custom short codes)

6️⃣ 🔐 User Login (Authentication)
📘 Description

This endpoint allows existing users to log in using their email and password.
If the credentials are valid, the API returns a JWT token that should be included in the Authorization header for accessing authenticated routes (like analytics or personal short URLs).

📍 Endpoint

POST /api/auth/user/login

🧾 Request Body
{
  "email": "user@example.com",
  "password": "securePassword123"
}

✅ Example Success Response
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6..."
}

⚠️ Error Response

If the credentials are invalid or user not found:

{
  "message": "Invalid email or password"
}

⚙️ Controller Snippet
@PostMapping("/user/login")
public ResponseEntity<Object> userlogin(@RequestBody AuthRequest request, HttpServletResponse response) {
    AuthenticationToken token = authService.login(request, response);
    return ResponseEntity.ok(token);
}

🧠 Highlights

🔒 Authenticates users using email and password

🪪 Returns a JWT token for secure access to protected endpoints

⚙️ Token must be sent in the Authorization header as:

Authorization: Bearer <token>


🌐 Required for personalized URL creation and analytics
