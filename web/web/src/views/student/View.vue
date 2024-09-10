<template>
    <div>
        <div class="card">
            <div class="card-header">
                <b>Add Students</b>
                <RouterLink to="/student/create" class="btn btn-primary float-end">
                    Add Student
                </RouterLink>
            </div>
            <div class="card-body">
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>No</th>
                            <th>Id</th>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Phone</th>
                            <th>Dob</th>
                            <th>Course</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(student, index) in paginatedData.content" :key="index">
                            <td>{{ index + 1 }}</td>
                            <td>{{ student.id }}</td>
                            <td>{{ student.name }}</td>
                            <td>{{ student.email }}</td>
                            <td>{{ student.phone }}</td>
                            <td>{{ student.dob }}</td>
                            <td>{{ student.course }}</td>
                            <td>
                                <RouterLink :to="{name : 'student-create-update', params: {id:student.id} }" class="btn btn-primary">Edit</RouterLink>&nbsp;
                                <button type="button" @click="deleteStudent(student.id)" class="btn btn-danger">Delete</button>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <!--pagination start-->
                <div class="pagination-controls">
                    <button @click="previousPage">Previous</button>
                    <span>Page {{ paginatedData.pageable.pageNumber + 1 }} of {{ paginatedData.totalPages }}</span>
                    <button @click="nextPage">Next</button>
                </div>
                <!--pagination end-->
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    name: 'students',
    data() {
        return {
            paginatedData: {
                content: [],
                pageable: {
                    pageNumber: 0,
                    pageSize: 10
                },
                totalPages: 0,
                last: true,
                first: true
            },
            page: 0,
            size: 5
        }
    },
    mounted() {
        console.log('im here..')
        this.fetchStudentPerPage(this.page, this.size)
    },
    methods: {
        fetchStudentPerPage(page, size) {
            axios.get('http://localhost:8081/api/v1/student/search?page=' + this.page + '&size=' + this.size).then(response => {
                this.paginatedData = response.data.data
                console.log("data students = ", JSON.stringify(this.paginatedData))
            }).catch(error => {
                console.log('error fetching data students : ', error)
            })
        },
        //go to the previous page
        previousPage() {
            if (this.paginatedData.pageable.pageNumber > 0) {
                this.page--;
                this.fetchStudentPerPage(this.page, this.size)
            }
        },
        //go to the next page
        nextPage() {
            if (!this.paginatedData.last) {
                this.page++;
                this.fetchStudentPerPage(this.page, this.size)
            }
        },
        //delete student by id
        deleteStudent(id) {
            if (confirm('are you sure you want delete this data ?')) {
                axios.delete('http://localhost:8081/api/v1/student/delete/' + id)
                    .then(response => {
                        console.log(JSON.stringify(response))
                        this.fetchStudentPerPage(this.page, this.size);
                    }).catch(error => {
                        console.log(error);
                    });
            }
        }
    }
}
</script>
<style>
.pagination-controls {
    margin-top: 20px;
}

.pagination-controls button {
    margin-right: 10px;
}
</style>