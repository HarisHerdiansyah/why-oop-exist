#include <iostream>
#include <chrono>

int main() {
	std::chrono::time_point time = std::chrono::system_clock::now();
	std::cout << time;
	return 0;
}