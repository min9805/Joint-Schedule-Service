import { Scheduler } from "@aldabil/react-scheduler";
import * as eventsApi from '../../apis/events';

export default function BasicScheduler({ events }) {
    return <Scheduler events={events}
        view="week"

        onConfirm={handleConfirm}
        // onConfirm={(event, action) => {
        //     // 이벤트와 액션을 받아와서 콘솔에 출력합니다.
        //     console.log("Event:", event);
        //     console.log("Action:", action);
        //     // 이벤트를 변경하거나 다른 동작을 수행할 수 있습니다.
        //     // 이 함수는 Promise를 반환해야 합니다.
        //     return Promise.resolve(event);
        // }}
        // onEventClick={(event) => {
        //     console.log("Clicked Event:", event);
        //     // 다른 작업을 수행할 수 있습니다.
        //     // 이벤트를 변경하거나 다른 동작을 수행할 수 있습니다.
        // }} onEventDrop={console.log("onEventDrop")}
        // onSelectedDateChange={(date) => {
        //     console.log("onSelectedDateChange");
        //     console.log(date);
        // }
        // }

        // onDelete={console.log("onDelete")}
        day={{
            startHour: 6,
            endHour: 24.
        }}
        week={{
            startHour: 6,
            endHour: 24.
        }}

        eventRenderer={({ event, ...props }) => {

            let backgroundColor = "#1976d2";

            // 이벤트에 color 속성이 있는지 확인하고 배경색을 지정
            if (event.color) {
                backgroundColor = event.color;
            }
            return (
                <div
                    style={{
                        display: "flex",
                        flexDirection: "column",
                        justifyContent: "space-between",
                        height: "100%",
                        background: backgroundColor
                    }}
                    {...props}
                >
                    <div
                        style={{ height: 20, background: "#ffffffb5", color: "black", fontSize: 13 }}
                    >
                        {event.start.toLocaleTimeString("en-US", {
                            timeStyle: "short"
                        })}
                    </div>
                    <div>{event.title}</div>
                    <div
                        style={{ height: 20, background: "#ffffffb5", color: "black", fontSize: 13 }}
                    >
                        {event.end.toLocaleTimeString("en-US", { timeStyle: "short" })}
                    </div>
                </div>
            );

        }}
    />;
}

const handleConfirm = async (event, action) => {
    try {
        let response;
        if (action === "create") {
            // createEvent() 함수를 사용하여 이벤트를 생성합니다.
            response = await eventsApi.createEvent(event);
        } else if (action === "edit") {
            // updateEvent() 함수를 사용하여 이벤트를 업데이트합니다.
            response = await eventsApi.updateEvent(event);
        }

        console.log(event);

        // 응답에서 data, status, headers 등의 정보를 추출합니다.
        const { data, status, headers } = response;

        // 여기서 필요한 작업을 수행합니다.
        console.log("Event Data:", data);
        console.log("Status:", status);
        console.log("Headers:", headers);
        event.event_id = data.data.id

        // 작업이 완료되면 Promise를 성공적으로 해결합니다.
        return new Promise((res, rej) => {
            res({
                ...event,
            });
        });
    } catch (error) {
        // 에러가 발생한 경우 처리합니다.
        console.error("Error:", error);
        // 에러를 throw하여 호출자에게 전달합니다.
        throw error;
    }
}