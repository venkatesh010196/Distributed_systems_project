import datetime
import socket
import threading

import time
from dateutil import parser

IP = '127.0.0.1'
TOTAL_SLAVE_NODE_OR_CLIENT = 3


def start_sending_time_to_server(slave_client, client_id):
    while True:
        time_to_send = datetime.datetime.now()
        client_data = f"{client_id}@{time_to_send}".encode()
        print(f"Client machine#{client_id} before sync, time is: {time_to_send}\n")
        slave_client.send(client_data)
        time.sleep(15)


def start_receiving_time_from_server(slave_client):
    while True:
        synchronized_server_response = slave_client.recv(1024).decode()
        synchronized_server_formatted_data = synchronized_server_response.split('@')
        client_id = synchronized_server_formatted_data[0]

        synchronized_time = parser.parse(synchronized_server_formatted_data[1])
        print(f"Client machine#{client_id} after sync, time is: {str(synchronized_time)}\n")


def initiate_slave_client_to_connect_server(client_id, port=8080):
    slave_client = socket.socket()

    slave_client.connect((IP, port))

    print("Starting to receive time from server")
    send_time_thread = threading.Thread(target=start_sending_time_to_server, args=(slave_client, client_id))
    send_time_thread.start()

    print("Starting to receiving synchronized time from server")
    receive_time_thread = threading.Thread(target=start_receiving_time_from_server, args=(slave_client,))
    receive_time_thread.start()


if __name__ == '__main__':
    for client_machine_id in range(0, TOTAL_SLAVE_NODE_OR_CLIENT):
        initiate_slave_client_to_connect_server(client_machine_id, 8080)