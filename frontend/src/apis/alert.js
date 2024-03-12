import Swal from 'sweetalert2';
import withReactContent from 'sweetalert2-react-content';
import * as AuthAPI from './auth';

/**
 * icon : success, error, warning, info, question
 */

const MySwal = withReactContent(Swal)

// 기본 alert
export const alert = (title, text, icon, callback) => {
    MySwal.fire({
        title: title,
        text: text,
        icon: icon,
    })
        .then(callback)
}

// confirm
export const confirm = (title, text, icon, callback) => {
    MySwal.fire({
        title: title,
        text: text,
        icon: icon,
        showCancelButton: true,
        cancelButtonColor: "#d33",
        cancelButtonText: "No",
        confirmButtonColor: "#3085d6",
        confirmButtonText: "Yes",
    })
        .then(callback)
}

//그룹 생성 alert
export const confirmGroupCreationWithNameInput = (title, text, callback) => {
    MySwal.fire({
        title: title,
        text: text,
        showCancelButton: true,
        cancelButtonColor: "#d33",
        cancelButtonText: "No",
        confirmButtonColor: "#3085d6",
        confirmButtonText: "Yes",
        input: 'text',
        inputPlaceholder: 'Enter group name',
        inputValidator: (value) => {
            if (!value) {
                return 'You need to enter a group name!'
            }
        }
    })
        .then((result) => {
            if (result.isConfirmed) {
                var data = { "name": result.value }
                callback(data);
            }
        });
};


// 그룹 내 그룹원 추가 alert
export const confirmGroupMemberAdditionWithNameInput = (groupId, callback) => {
    MySwal.fire({
        title: "그룹원의 이름을 검색해주세요.",
        input: "text",
        inputAttributes: {
            autocapitalize: "off"
        },
        showCancelButton: true,
        cancelButtonText: "취소",
        confirmButtonText: "검색",
        showLoaderOnConfirm: true,
        preConfirm: async (loginId) => {
            try {
                var inputData = { "loginId": loginId }
                const response = await AuthAPI.findMember(inputData);
                const { data, status, headers } = response;
                console.log("data", data);
                console.log("data resultCode", data.resultCode);
                if (data.resultCode !== "SUCCESS") {
                    console.log("success", data.data);
                    return Swal.showValidationMessage(data.data);
                }
                console.log("message");
                return data.data;
            } catch (error) {
                console.log("error", error);
                Swal.showValidationMessage(error.response.data.data.id);
            }
        },
        allowOutsideClick: () => !Swal.isLoading()
    }).then((result) => {
        console.log("result", result);
        if (result.isConfirmed) {
            MySwal.fire({
                title: `${result.value.loginId}'s avatar`,
                imageUrl: process.env.PUBLIC_URL + result.value.avator,
                showCancelButton: true,
                cancelButtonText: "취소",
                confirmButtonText: "추가",
                showLoaderOnConfirm: true,
            }).then((addResult) => {
                if (addResult.isConfirmed) {
                    var data = { "id": result.value.id }
                    callback(data); // 검색된 멤버의 ID를 callback으로 전달
                }
            });
        }
    });
};


