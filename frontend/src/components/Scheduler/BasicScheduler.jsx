import { Scheduler } from "@aldabil/react-scheduler";

export default function BasicScheduler({ events }) {
    return <Scheduler events={events}

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