<?php
$conn = new mysqli("localhost", "root", "mysql", "anmp_160420016") or die("Connection error: " . $conn->error);

$arr = [];

extract($_POST);

$sql = "SELECT username FROM users WHERE username=?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("s", $username);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows > 0) {
    $arr = ["result" => "error", "message" => "User " . $username . " already registered."];
} else {
    $sql = "INSERT INTO users(username, password) VALUES(?, ?)";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("ss", $username, $password);
    $stmt->execute();

    if ($stmt->affected_rows > 0) {
        $arr = ["result" => "success", "message" => "User registered successfuly."];
    } else if (!$conn->errno) {
        $arr = ["result" => "failed", "message" => "Something wrong with our server."];
    } else {
        $arr = ["result" => "error", "message" => "sql error: $conn->error"];
    }
}

$stmt->close();
echo json_encode($arr);
