<?php
include "1.php";

function get_factors($num){
    $factors = [];
    for ($i=1; $i <= sqrt($num); $i++) {
        if($num % $i == 0){
            $factors[] = $i;
            if($i ** 2 != $num){
                $factors[] = $num/$i;
            }
        }
    }
    sort($factors);
    return $factors;
}

function is_invalid_p2($id) {
    if (is_invalid_p1($id)){
        return true;
    }

    $id = (string)$id;
    foreach(get_factors(strlen($id)) as $part_len) {
        $is_invalid = true;

        $parts = str_split($id, $part_len);
        $first_part = $parts[0];
        foreach ($parts as $part) {
            if ($part != $first_part) {
                $is_invalid = false;
            }
        }
        if ($is_invalid && ($part_len != strlen($id))) {
            return true;
        }
    }
    return false;
}
function get_sum_invalid_ids_p2($start, $end) {
    $sum = 0;
    for($id = $start; $id <= $end; $id++){
        if(is_invalid_p2($id)){
            $sum += $id;
        }
    }
    return $sum;
}

//main
$answer = 0;
foreach($ranges as $range){
    $answer += get_sum_invalid_ids_p2($range[0], $range[1]);
}
print("p2: " . $answer);
//11323661261
