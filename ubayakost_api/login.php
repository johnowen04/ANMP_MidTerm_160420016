<?php
require_once('env.php');

$conn = new mysqli($_ENV['SERVER_NAME'], $_ENV['USER_NAME'], $_ENV['USER_PASSWORD'], $_ENV['DATABASE_NAME']) or die("Connection error: " . $conn->error);

$arr = [];

extract($_POST);

$sql = "SELECT * FROM users WHERE username=? AND password=?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("ss", $username, $password);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows > 0) {
    $data = [];
    while ($row = $result->fetch_assoc()) {
        $data = [
            "username" => $row["username"],
            "firstname" => $row["firstname"],
            "lastname" => $row["lastname"],
        ];
    }

    $arr = ["result" => "success", "message" => "Welcome to the app, ".$username."!", "data" => $data];
} else if (!$conn->errno) {
    $arr = ["result" => "failed", "message" => "Username or password is wrong."];
} else {
    $arr = ["result" => "error", "message" => "sql error: $conn->error"];
}

$stmt->close();
echo json_encode($arr);