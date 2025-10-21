from datetime import datetime

def main():
    input("start time via enter")
    start_time = datetime.now()

    input("end time via enter")
    end_time = datetime.now()

    st_year = start_time.year
    st_month = start_time.month
    st_day = start_time.day
    st_hour = start_time.hour
    st_minute = start_time.minute
    st_second = start_time.second

    print("\n")

    et_year = end_time.year
    et_month = end_time.month
    et_day = end_time.day
    et_hour = end_time.hour
    et_minute = end_time.minute
    et_second = end_time.second

    print(st_year, st_month, st_day, st_hour, st_minute, st_second)
    print(et_year, et_month, et_day, et_hour, et_minute, et_second)

    delta_time = end_time - start_time
    print(delta_time.total_seconds())

if __name__ == '__main__': main()