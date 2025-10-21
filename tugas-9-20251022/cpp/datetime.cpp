#include <iostream>
#include <chrono>
#include <ctime>

int main() {	
	std::string temp = "";
	std::cout << "start time via enter: "; std::getline(std::cin, temp);
	auto start_time = std::chrono::system_clock::now();
	std::cout << "end time via enter: "; std::getline(std::cin, temp);
	auto end_time = std::chrono::system_clock::now();
	
	
	std::time_t st = std::chrono::system_clock::to_time_t(start_time);
	std::time_t et = std::chrono::system_clock::to_time_t(end_time);
	
	std::tm local_sttm = *std::localtime(&st);
	std::tm local_ettm = *std::localtime(&et);
	
	std::cout << "\nYear   : " << 1900 + local_sttm.tm_year << "\n";
    std::cout << "Month  : " << 1 + local_sttm.tm_mon   << "\n";
    std::cout << "Day    : " << local_sttm.tm_mday      << "\n";
    std::cout << "Hour   : " << local_sttm.tm_hour      << "\n";
    std::cout << "Minute : " << local_sttm.tm_min       << "\n";
    std::cout << "Second : " << local_sttm.tm_sec       << "\n";
	
	std::cout << "\nYear   : " << 1900 + local_ettm.tm_year << "\n";
    std::cout << "Month  : " << 1 + local_ettm.tm_mon   << "\n";
    std::cout << "Day    : " << local_ettm.tm_mday      << "\n";
    std::cout << "Hour   : " << local_ettm.tm_hour      << "\n";
    std::cout << "Minute : " << local_ettm.tm_min       << "\n";
    std::cout << "Second : " << local_ettm.tm_sec       << "\n";
	
	std::chrono::duration<double> diff = end_time - start_time;
	std::cout << diff.count() << std::endl;
	return 0;
}
