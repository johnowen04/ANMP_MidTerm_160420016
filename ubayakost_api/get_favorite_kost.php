<?php
$conn = new mysqli("localhost", "root", "mysql", "anmp_160420016") or die("Connection error: ".$conn->error);

$arr = [];

extract($_POST);

$sql = "SELECT * FROM favorite_properties WHERE users_username=?";
$stmt = $conn->prepare($sql);
$stmt->bind_param('s', $username);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows > 0) {
    $data = [];
    while ($row = $result->fetch_assoc()) {
        $sql2 = "SELECT * FROM properties WHERE id=?";
        $stmt2 = $conn->prepare($sql2);
        $stmt2->bind_param('i', $row["properties_id"]);
        $stmt2->execute();
        $result2 = $stmt2->get_result();

        if ($result2->num_rows > 0) {
            while($row2 = $result2->fetch_assoc()) {
                $row2["is_favorite"] = true;
                array_push($data, $row2);
            }
        }
    }

    $arr = ["result" => "success", "message" => "Fetched favorite data.", "data" => $data];
} else if (!$conn->errno) {
    $arr = ["result" => "failed", "message" => "Something went wrong."];
} else {
    $arr = ["result" => "error", "message" => "sql error: $conn->error"];
}

$stmt->close();
echo json_encode($arr);