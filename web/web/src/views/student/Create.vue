<template>
    <div class="container mt-5">

        <div class="card">
            <div class="card-header">
                <h4>Add Students </h4>
                <h4 style="color: green;">{{ savedDataIsValid }}</h4>
            </div>
            <div class="card-body">
                <div class="mb-3">
                    <label for="name">Name</label>
                    <input v-model="formCreateStudent.name" type="text" class="form-control" />
                </div>
                <div class="mb-3">
                    <label for="email">Email</label>
                    <input v-model="formCreateStudent.email" type="text" class="form-control" />
                    <p v-if="emailIsAlreadyExistMsg" style="color: red;">{{ emailIsAlreadyExistMsg }}</p>
                </div>
                <div class="mb-3">
                    <label for="phone">Phone</label>
                    <input v-model="formCreateStudent.phone" type="text" class="form-control" />
                    <p v-if="phoneIsAlreadyExistMsg" style="color: red;">{{ phoneIsAlreadyExistMsg }}</p>
                </div>
                <div class="mb-3">
                    <label for="course">Course</label>
                    <input v-model="formCreateStudent.course" type="text" class="form-control" />
                </div>
                <div class="mb-3">
                    <label for="dob">Dob</label>
                    <Datepicker class="form-control" v-model="formCreateStudent.dob" />
                </div>
            </div>
            <div class="card-footer">
                <button @click="saveStudent" type="button" class="btn btn-primary">Save</button>&nbsp;
                <RouterLink class="btn btn-danger" to="/student">Back</RouterLink>
            </div>
        </div>
    </div>
</template>
<script>
import axios from 'axios'
import Datepicker from 'vue3-datepicker'
import { format } from 'date-fns'

export default {
    name: 'create',
    components: {
        Datepicker
    },
    data() {
        return {
            formCreateStudent: {
                id: 0,
                name: '',
                email: '',
                phone: '',
                course: '',
                dob: '',
            },
            savedDataIsValid: '',
            emailIsAlreadyExistMsg: '',
            phoneIsAlreadyExistMsg: '',
        }
    },
    mounted() {
        console.log("[create] : im here !")
        if (this.$route.params.id != null) {
            const id = this.$route.params.id;
            console.log("params id = ", this.$route.params.id);
            this.findStudent(id)
        }
    },
    computed: {
        dateFormat: function () {
            let date = new Date(this.formCreateStudent.dob)
            const padZero = (num) => {
                return num < 10 ? '0' + num : num
            }

            return date.getFullYear() + '-' +
                padZero(date.getMonth() + 1) + '-' +
                padZero(date.getDate());
        }
    },
    methods: {
        clearForms() {
            this.formCreateStudent = {}
        },
        saveStudent() {
            if (this.$route.params.id != null) {
                console.log("update student..")
                console.log(JSON.stringify(this.formCreateStudent));
                axios.put('http://localhost:8081/api/v1/student/update/'+this.$route.params.id,this.formCreateStudent)
                    .then(response => {
                        console.log('success', response)
                        this.emailIsAlreadyExistMsg = '';
                        this.phoneIsAlreadyExistMsg = '';
                        this.savedDataIsValid = 'Success Update Data !'
                        this.clearForms();
                    }).catch(error => {
                        console.log('error status : ', error.status)
                        if (error.response.data.message == 'email is already exist !') {
                            this.emailIsAlreadyExistMsg = error.response.data.message;
                            this.phoneIsAlreadyExistMsg = '';
                        } else if (error.response.data.message == 'phone number is already exist !') {
                            this.emailIsAlreadyExistMsg = '';
                            this.phoneIsAlreadyExistMsg = error.response.data.message;
                        } else {
                            this.emailIsAlreadyExistMsg = '';
                            this.phoneIsAlreadyExistMsg = '';
                        }
                        console.log('error email : ', JSON.stringify(error.response.data.message))
                    })
            } else {
                console.log("save student..")
                axios.post('http://localhost:8081/api/v1/student/save', this.formCreateStudent)
                    .then(response => {
                        console.log('success', response)
                        this.emailIsAlreadyExistMsg = '';
                        this.phoneIsAlreadyExistMsg = '';
                        this.savedDataIsValid = 'Success Saved Data !'
                        this.clearForms();
                    }).catch(error => {
                        console.log('error status : ', error.status)
                        if (error.response.data.message == 'email is already exist !') {
                            this.emailIsAlreadyExistMsg = error.response.data.message;
                            this.phoneIsAlreadyExistMsg = '';
                        } else if (error.response.data.message == 'phone number is already exist !') {
                            this.emailIsAlreadyExistMsg = '';
                            this.phoneIsAlreadyExistMsg = error.response.data.message;
                        } else {
                            this.emailIsAlreadyExistMsg = '';
                            this.phoneIsAlreadyExistMsg = '';
                        }
                        console.log('error email : ', JSON.stringify(error.response.data.message))
                    })
            }
        },
        findStudent(id) {
            console.log("find student by id = ", id);
            axios.get('http://localhost:8081/api/v1/student/find/' + id)
                .then(response => {
                    console.log(JSON.stringify(response))
                    this.formCreateStudent = {
                        id: response.data.data.id,
                        name: response.data.data.name,
                        email: response.data.data.email,
                        phone: response.data.data.phone,
                        course: response.data.data.course,
                        dob: new Date(response.data.data.dob),
                    }
                }).catch(error => {
                    console.log(error);
                });
        },
    }
}
</script>