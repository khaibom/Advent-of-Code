package main

import (
	"bufio"
	"fmt"
	"os"
	"slices"
	"sort"
)

func getInput(path string) ([][]rune, error) {
	file, err := os.Open(path)
	if err != nil {
		return nil, err
	}
	defer file.Close()

	var grid [][]rune
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		line := scanner.Text()
		grid = append(grid, []rune(line))
	}

	if err := scanner.Err(); err != nil {
		return nil, err
	}
	return grid, nil
}

func findPosInLine(line []rune, char rune) []int {
	s := []int{}
	for i, character := range line {
		if character == char {
			s = append(s, i)
		}
	}
	return s
}

func removeDuplicates(nums []int) []int {
	seen := map[int]bool{}
	result := make([]int, 0, len(nums))
	for _, num := range nums {
		if !seen[num] {
			seen[num] = true
			result = append(result, num)
		}
	}
	return result
}

func p1(lines [][]rune) {
	split_positions := []int{}
	count := 0
	for i, line := range lines {
		if i == 0 {
			s_pos := findPosInLine(line, 'S')
			split_positions = append(split_positions, s_pos...)
			continue
		}
		new_potential_split_positions := findPosInLine(line, '^')
		if len(new_potential_split_positions) == 0 {
			continue
		}
		new_split_positions := []int{}
		for _, split_position := range split_positions {
			if slices.Contains(new_potential_split_positions, split_position) {
				new_split_positions = append(new_split_positions, split_position-1, split_position+1)
				count++
				continue
			}
			new_split_positions = append(new_split_positions, split_position)
		}
		new_split_positions = removeDuplicates(new_split_positions)
		sort.Ints(new_split_positions)
		split_positions = new_split_positions
	}
	fmt.Println(count)
}

func p2(lines [][]rune) {
	timelines := make(map[int]int)
	for i, line := range lines {
		if i == 0 {
			s_pos := findPosInLine(line, 'S')
			timelines[s_pos[0]] = 1
			continue
		}
		new_potential_split_positions := findPosInLine(line, '^')
		if len(new_potential_split_positions) == 0 {
			continue
		}
		splitter_set := make(map[int]struct{}, len(new_potential_split_positions))
		for _, new_potential_split_position := range new_potential_split_positions {
			splitter_set[new_potential_split_position] = struct{}{}
		}

		next := make(map[int]int)
		for col, count := range timelines {
			if _, is_splitter := splitter_set[col]; is_splitter {
				next[col-1] += count
				next[col+1] += count
			} else {
				next[col] += count
			}
			timelines = next
		}
	}

	result := 0
	for _, count := range timelines {
		result += count
	}
	fmt.Println(result)
}

func main() {
	input, err := getInput("input.txt")
	if err != nil {
		panic(err)
	}
	p1(input) //1658
	p2(input) //53916299384254
}
