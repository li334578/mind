<template>
  <div id="app">
    <a-button class="editable-add-btn" @click="handleAdd">
      Add
    </a-button>
    <a-select style="width: 120px" :value="filterType" :allowClear="true" @change="filterByType">
      <a-icon slot="suffixIcon" type="smile"/>
      <a-select-option :value="1">
        临时存储
      </a-select-option>
      <a-select-option :value="2">
        长时存储
      </a-select-option>
      <a-select-option :value="3">
        永久存储
      </a-select-option>
    </a-select>
    <a-select style="width: 120px" :value="filterDelete" :allowClear="true" @change="filterByIsDelete">
      <a-icon slot="suffixIcon" type="smile"/>
      <a-select-option value="0">
        未删除
      </a-select-option>
      <a-select-option value="1">
        已删除
      </a-select-option>
    </a-select>
    <a-input-search placeholder="输入搜索内容" style="width: 200px" @search="filterByContent"/>
    <a-table bordered :pagination="bubblePagination" @change="updateBubbleTable" :data-source="dataList"
             :columns="columns">
      <template slot="content" slot-scope="text, record">
        <editable-cell :text="text" @change="onCellChange(record.id, 'content', $event)"/>
      </template>
      <template slot="type" slot-scope="text, record, index">
        <span v-if="text === 1">临时存储</span>
        <span v-if="text === 2">长时存储</span>
        <span v-if="text === 3">永久存储</span>
      </template>
      <template slot="isDelete" slot-scope="text, record, index">
        <span v-if="text === 0">未删除</span>
        <span v-if="text === 1">已删除</span>
      </template>
      <template slot="operation" slot-scope="text, record">
        <a-button type="link" @click="onUpdate(record.id)">
          修改
        </a-button>
        <a-popconfirm
          v-if="dataList.length"
          title="确定要删除吗?"
          ok-text="确定" cancel-text="取消"
          @confirm="() => onDelete(record.id)"
        >
          <a href="javascript:;">删除</a>
        </a-popconfirm>
      </template>
    </a-table>
    <a-modal v-model="visibleAddBubble" title="新增Bubble" ok-text="确认" cancel-text="取消" @ok="commitBubble"
             destroyOnClose:=true>
      <a-form-item label="内容">
        <a-textarea placeholder="请输入内容" :allowClear=true v-model="bubble.content" auto-size/>
      </a-form-item>
      <div style="margin: 24px 0"/>
      <a-form-item label="类型">
        <a-radio-group v-model="bubble.type">
          <a-radio-button :value="1">
            临时存储
          </a-radio-button>
          <a-radio-button :value="2">
            长时存储
          </a-radio-button>
          <a-radio-button :value="3">
            永久存储
          </a-radio-button>
        </a-radio-group>
      </a-form-item>
      <div :style="{ marginTop: '16px' }"/>
      <a-form-item label="优先级">
        <a-input-number label="优先级" id="inputNumber" v-model="bubble.priorityValue" :min="0" :max="100"/>
      </a-form-item>
    </a-modal>

    <a-modal v-model="visibleUpdateBubble" title="修改Bubble" ok-text="确认" cancel-text="取消" @ok="updateBubble"
             destroyOnClose:=true>
      <span>内容</span>
      <a-textarea placeholder="请输入内容" :allowClear=true v-model="bubble.content" auto-size/>
      <div style="margin: 24px 0"/>
      <a-radio-group v-model="bubble.type">
        <a-radio-button :value="1">
          临时存储
        </a-radio-button>
        <a-radio-button :value="2">
          长时存储
        </a-radio-button>
        <a-radio-button :value="3">
          永久存储
        </a-radio-button>
      </a-radio-group>
      <div :style="{ marginTop: '16px' }"/>
      <a-input-number label="优先级" id="inputNumber" v-model="bubble.priorityValue" :min="0" :max="100"/>
    </a-modal>
  </div>
</template>
<script>

const EditableCell = {
  template: `
    <div class="editable-cell">
    <div v-if="editable" class="editable-cell-input-wrapper">
      <a-input :value="value" @change="handleChange" @pressEnter="check"/>
      <a-icon
        type="check"
        class="editable-cell-icon-check"
        @click="check"
      />
    </div>
    <div v-else class="editable-cell-text-wrapper">
      {{ value || ' ' }}
      <a-icon type="edit" class="editable-cell-icon" @click="edit"/>
    </div>
    </div>
  `,
  props: {
    text: String,
  },
  data() {
    return {
      value: this.text,
      editable: false,
    };
  },
  methods: {
    handleChange(e) {
      const value = e.target.value;
      this.value = value;
    },
    check() {
      this.editable = false;
      this.$emit('change', this.value);
    },
    edit() {
      this.editable = true;
    },
  },
};

let dataList;
export default {
  components: {
    EditableCell,
  },
  name: 'Main',
  mounted: function () {
    this.pageBubble()
  },
  data() {
    return {
      visibleAddBubble: false,
      visibleUpdateBubble: false,
      dataList,
      bubblePagination: {
        current: 1,
        showSizeChanger: true,
        showQuickJumper: true,
        defaultPageSize: 10,
        total: 0,
        pageSize: 10,
        showTotal: total => `共 ${total} 条数据`,
      },
      columns: [
        {
          title: '内容',
          dataIndex: 'content',
          width: '30%',
          scopedSlots: {customRender: 'content'},
        },
        {
          title: '类型',
          dataIndex: 'type',
          scopedSlots: {customRender: 'type'},
        },
        {
          title: '更新时间',
          dataIndex: 'updateTime',
        },
        {
          title: '优先级',
          dataIndex: 'priorityValue',
        },
        {
          title: '是否删除',
          dataIndex: 'isDelete',
          scopedSlots: {customRender: 'isDelete'},
        },
        {
          title: 'operation',
          dataIndex: 'operation',
          scopedSlots: {customRender: 'operation'},
        },
      ],
      bubble: {
        id: '',
        content: '',
        type: 1,
        priorityValue: 0
      },
      filterType: undefined,
      filterDelete: undefined,
      filterContent: undefined,
    }
  },
  methods: {
    onCellChange(key, dataIndex, value) {
      this.$message.loading({content: 'Loading...', key});
      const dataList = [...this.dataList];
      const target = dataList.find(item => item.key === key);
      if (target) {
        // 更新
        this.$axios.post("/bubble/", {id: key, content: value}).then((res) => {
          this.pageBubble();
          this.$message.success({content: 'Loaded!', key, duration: 2});
        });
      }
    },
    onDelete(id) {
      const dataList = [...this.dataList];
      this.dataList = dataList.filter(item => item.key !== id);
      this.$axios.delete("/bubble/" + id).then((res) => {
        this.$message.success("删除成功");
      })
      this.pageBubble();
    },
    onUpdate(id) {
      const dataList = [...this.dataList];
      const target = dataList.find(item => item.key === id);
      this.visibleUpdateBubble = true
      if (target) {
        this.bubble = target
      }
    },
    handleAdd() {
      this.visibleAddBubble = true;
    },
    pageBubble() {
      let pageRequest = {
        currentPage: this.bubblePagination.current,
        pageSize: this.bubblePagination.pageSize,
        type: this.filterType,
        isDelete: this.filterDelete,
        content: this.filterContent,
      }
      // 查询数据
      this.$axios.post("/bubble/page", pageRequest).then((res) => {
        this.dataList = res.data.records.map(obj => ({key: obj.id, ...obj}))
        this.bubblePagination.total = res.data.total
      })
    },
    commitBubble() {
      if (!this.bubble.content) {
        this.$message.error('请输入内容');
        return;
      }
      // 提交后端
      this.$axios.put("/bubble/", this.bubble).then((res) => {
        this.bubble.content = '';
        this.bubble.type = 1;
        this.bubble.priorityValue = 0;
        this.pageBubble();
      })
      this.visibleAddBubble = false;
    },
    updateBubble() {
      if (!this.bubble.content) {
        this.$message.error('请输入内容');
        return;
      }
      let willBubble = {
        id: this.bubble.id,
        content: this.bubble.content,
        type: this.bubble.type,
        priorityValue: this.bubble.priorityValue
      }
      // 根据id更新
      this.$axios.post("/bubble/", willBubble).then((res) => {
        this.bubble.content = '';
        this.bubble.type = 1;
        this.bubble.priorityValue = 0;
        this.pageBubble();
      })
      this.visibleUpdateBubble = false;
    },
    filterByType(value) {
      this.filterType = value;
      this.pageBubble();
    },
    filterByIsDelete(value) {
      this.filterDelete = value;
      this.pageBubble();
    },
    filterByContent(value) {
      this.filterContent = value;
      this.pageBubble();
    },
    updateBubbleTable(value) {
      this.bubblePagination = value
      this.pageBubble();
    },
  },
}
</script>
<style>
.editable-cell {
  position: relative;
}

.editable-cell-input-wrapper,
.editable-cell-text-wrapper {
  padding-right: 24px;
}

.editable-cell-text-wrapper {
  padding: 5px 24px 5px 5px;
}

.editable-cell-icon,
.editable-cell-icon-check {
  position: absolute;
  right: 0;
  width: 20px;
  cursor: pointer;
}

.editable-cell-icon {
  line-height: 18px;
  display: none;
}

.editable-cell-icon-check {
  line-height: 28px;
}

.editable-cell:hover .editable-cell-icon {
  display: inline-block;
}

.editable-cell-icon:hover,
.editable-cell-icon-check:hover {
  color: #108ee9;
}

.editable-add-btn {
  margin-bottom: 8px;
}
</style>
