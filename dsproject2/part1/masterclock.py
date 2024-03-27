import datetime
import socket
import threading

import time
from dateutil import parser

store_client_data = {}


def start_receiving_clock_time_from_client(connector, address):
    while True:
        client_decoded_string = connector.recv(1024).decode()
        client_formatted_data = client_decoded_string.split('@')
        client_id = client_formatted_data[0]
        clock_time_string = client_formatted_data[1]
        clock_time = parser.parse(clock_time_string)
        clock_time_diff = datetime.datetime.now() - clock_time

        store_client_data[address] = {
            "client_id": client_id,
            "clock_time": clock_time,
            "time_difference": clock_time_diff,
            "connector": connector
        }

        print(f"Client Data updated with: {address}")
        time.sleep(5)


def establish_client_connection(master_server):
    while True:
        master_slave_connector, addr = master_server.accept()
        slave_address = str(addr[0]) + ":" + str(addr[1])

        print(slave_address + " got connected successfully")

        current_thread = threading.Thread(target=start_receiving_clock_time_from_client,
                                          args=(master_slave_connector, slave_address,))
        current_thread.start()


def get_average_clock_diff():
    time_difference_list = []
    for client_addr, client in store_client_data.items():
        time_difference_list.append(client['time_difference'])
    sum_of_clock_difference = sum(time_difference_list, datetime.timedelta(0, 0))
    average_clock_difference = sum_of_clock_difference / len(store_client_data)
    return average_clock_difference


def synchronize_all_clocks():
    while True:
        print("New synchronization cycle started.")
        print(f"Number of clients to be synchronized: {len(store_client_data)}")
        if len(store_client_data) > 0:
            average_clock_difference = get_average_clock_diff()

            for client_addr, client in store_client_data.items():
                try:
                    # As we are in the same machine, therefore the time difference will be very minimal.
                    # for the simplicity we just add the time difference with the master clock current time
                    synchronized_time = datetime.datetime.now() + average_clock_difference
                    server_response_data = f"{client['client_id']}@{synchronized_time}".encode()
                    client['connector'].send(server_response_data)
                except Exception as e:
                    print(f"Something went wrong while sending synchronized time through {client_addr}")
                    print("=======Error details Begin=======")
                    print(e)
                    print("=======Error details Finish=======")

        else:
            print("No client data. Synchronization not applicable.")
        print("")
        time.sleep(5)


def initialize_clock_server(port=8080):
    master_server = socket.socket()
    master_server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    print("Socket at master node created successfully")

    master_server.bind(('', port))

    # Start listening to requests
    master_server.listen(10)
    print("Clock server started .......")

    # start making connections
    print("Starting to make connections.......")
    master_thread = threading.Thread(target=establish_client_connection, args=(master_server,))
    master_thread.start()

    # start synchronization
    print("Starting synchronization parallelly........")
    sync_thread = threading.Thread(target=synchronize_all_clocks, args=())
    sync_thread.start()


if __name__ == '__main__':
    initialize_clock_server(port=8080)
